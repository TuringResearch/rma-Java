package br.pro.turing.masiot.rml;

import br.pro.turing.masiot.core.model.Action;
import br.pro.turing.masiot.core.model.Data;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.service.ServiceManager;
import br.pro.turing.masiot.core.utils.LoggerUtils;
import com.google.gson.reflect.TypeToken;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.objects.ApplicationObject;
import lac.cnet.sddl.objects.Message;
import lac.cnet.sddl.objects.PrivateMessage;
import lac.cnet.sddl.udi.core.SddlLayer;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;
import lac.cnet.sddl.udi.core.listener.UDIDataReaderListener;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The RML is capable of maintaining updated information from IoT Objects hosted in environments and running over
 * an IoT middleware. The Resource Management Component (RMC) manages (1) the registering process of IoT Objects;
 * (2) the resource's data updating process in the Virtualized Components Database (VCDB), and; (3) the actions
 * that must be executed by IoT Objects. Besides, it also capable of exposing services for providing access for
 * clients to visualize environments and its resources and for agents to access physical resources.
 */
public class RMLServer implements UDIDataReaderListener<ApplicationObject> {

    /** Logger. */
    private static final Logger LOGGER = LoggerUtils.initLogger(
            RMLServer.class.getClassLoader().getResourceAsStream("br/pro/turing/masiot/rml/rml.logging.properties"),
            RMLServer.class.getSimpleName());

    /** Security Descriptor Definition Language layer. */
    private SddlLayer core;

    /**
     * Constructor.
     */
    public RMLServer() {
    }

    /**
     * Starts the Resource Management Layer.
     */
    public void start() {
        LOGGER.info("Starting RML server...");
        this.core = UniversalDDSLayerFactory.getInstance();
        this.core.createParticipant(UniversalDDSLayerFactory.CNET_DOMAIN);

        this.core.createPublisher();
        this.core.createSubscriber();

        Object receivedMessageTopic = this.core.createTopic(Message.class, Message.class.getSimpleName());
        this.core.createDataReader(this, receivedMessageTopic);

        Object toMobileNodeTopic = this.core.createTopic(PrivateMessage.class, PrivateMessage.class.getSimpleName());
        this.core.createDataWriter(toMobileNodeTopic);
        LOGGER.info("RML server running.");

        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                LOGGER.severe("Interrupted Thread Exception.");
            }
        }
    }

    /**
     * Treats the new data.
     * <p>
     * If new data is a Device, try to register or login the device.
     * If new data is an Action, try to send an action to the target device.
     * If new data is a list of Data, try to uodate the VCDB with device data list.
     *
     * @param topicSample Topic sample.
     */
    @Override
    public void onNewData(ApplicationObject topicSample) {
        LOGGER.fine("New message received");

        Message message = (Message) topicSample;
        String javaObject = (String) Serialization.fromJavaByteStream(message.getContent());

        if (ServiceManager.getInstance().jsonService.jasonIsObject(javaObject, Device.class.getName())) {
            Device newDevice = ServiceManager.getInstance().jsonService.fromJson(javaObject, Device.class);
            startDevice(message, newDevice);
        } else if (ServiceManager.getInstance().jsonService.jasonIsObject(javaObject, Action.class.getName())) {
            Action newAction = ServiceManager.getInstance().jsonService.fromJson(javaObject, Action.class);
            delegateAction(newAction);
        } else if (ServiceManager.getInstance().jsonService.jasonIsObject(javaObject, Data.class.getName())) {
            final Type dataListType = new TypeToken<ArrayList<Data>>() {
            }.getType();
            ArrayList<Data> dataArrayList = ServiceManager.getInstance().jsonService.fromJson(javaObject, dataListType);
            if (dataArrayList != null && !dataArrayList.isEmpty()) {
                final Data data = dataArrayList.get(0);
                ServiceManager.getInstance().deviceService.updateLast(data.getDeviceName());
                ServiceManager.getInstance().dataService.saveAll(dataArrayList);
            }
        }
    }

    /**
     * Start a device in VCDB and send a message to this device informing that this device is ready to work.
     *
     * @param message   Message.
     * @param newDevice Device.
     */
    private void startDevice(Message message, Device newDevice) {
        LOGGER.info("Registering and logged in the device " + newDevice.getDeviceName() + ".");

        // If already exists a old device with the same deviceName the new device, the _id will be copied from old to
        // new device. This will ensure that the new device will be update instead of created in the database.
        final Device oldDevice = ServiceManager.getInstance().deviceService.findById(newDevice.getDeviceName());
        if (oldDevice != null) {
            LOGGER.info(
                    "Device " + newDevice.getDeviceName() + " was already registered. This Device will be logged in.");
        }
        newDevice.setGatewayUUID(message.getGatewayId().toString());
        newDevice.setUUID(message.getSenderId().toString());
        newDevice.setLocalLastUpdate(LocalDateTime.now());
        ServiceManager.getInstance().deviceService.save(newDevice);

        // Responding that device registration was successful.
        LOGGER.info("Device " + newDevice.getDeviceName() + " ready.");
        sendMessage(message.getGatewayId(), message.getSenderId(),
                ServiceManager.getInstance().jsonService.toJson(newDevice));
    }

    /**
     * Extract the target device for the passed action and send the action to this device.
     *
     * @param newAction Action.
     */
    private void delegateAction(Action newAction) {
        ServiceManager.getInstance().actionService.save(newAction);

        // Discovering the device that must perform this action.
        final Device deviceByCommand = ServiceManager.getInstance().deviceService.findById(newAction.getDeviceName());
        LOGGER.info("Delagating command to " + deviceByCommand.getDeviceName() + ".");
        UUID gatewayId = UUID.fromString(deviceByCommand.getGatewayUUID());
        UUID receiverId = UUID.fromString(deviceByCommand.getUUID());

        sendMessage(gatewayId, receiverId, ServiceManager.getInstance().jsonService.toJson(newAction));
    }

    /**
     * Send a message to a receiver through a gateway.
     *
     * @param gatewayId  Gateway UUID.
     * @param receiverId Receiver UUID.
     * @param content    Content of the message.
     */
    private void sendMessage(UUID gatewayId, UUID receiverId, Serializable content) {
        ApplicationMessage responseMessage = new ApplicationMessage();
        responseMessage.setContentObject(content);
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setGatewayId(gatewayId);
        privateMessage.setNodeId(receiverId);
        privateMessage.setMessage(Serialization.toProtocolMessage(responseMessage));
        this.core.writeTopic(PrivateMessage.class.getSimpleName(), privateMessage);
    }
}
