package br.pro.turing.masiot.rmlserver;

import br.pro.turing.masiot.core.model.*;
import br.pro.turing.masiot.core.service.ServiceManager;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.objects.ApplicationObject;
import lac.cnet.sddl.objects.Message;
import lac.cnet.sddl.objects.PrivateMessage;
import lac.cnet.sddl.udi.core.SddlLayer;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;
import lac.cnet.sddl.udi.core.listener.UDIDataReaderListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class RMLServer implements UDIDataReaderListener<ApplicationObject> {

    private static final Logger LOGGER = Logger.getLogger(RMLServer.class.getName());

    private SddlLayer core;

    public RMLServer() {
        LOGGER.info("Starting RML data reader...");
        this.core = UniversalDDSLayerFactory.getInstance();
        this.core.createParticipant(UniversalDDSLayerFactory.CNET_DOMAIN);

        this.core.createPublisher();
        this.core.createSubscriber();

        Object receivedMessageTopic = this.core.createTopic(Message.class, Message.class.getSimpleName());
        this.core.createDataReader(this, receivedMessageTopic);

        Object toMobileNodeTopic = this.core.createTopic(PrivateMessage.class, PrivateMessage.class.getSimpleName());
        this.core.createDataWriter(toMobileNodeTopic);
        LOGGER.info("RML data reader started.");

        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                LOGGER.severe("Interrupted Thread Exception.");
            }
        }
    }

    @Override
    public void onNewData(ApplicationObject topicSample) {
        LOGGER.fine("New message received");

        Message message = (Message) topicSample;
        Object javaObject = Serialization.fromJavaByteStream(message.getContent());
        if (javaObject instanceof Device) {
            Device newDevice = (Device) javaObject;
            startDevice(message, newDevice);
        } else if (javaObject instanceof Action) {
            Action newAction = (Action) javaObject;
            delegateAction(newAction);
        } else if (javaObject instanceof ArrayList) {
            if (!((ArrayList) javaObject).isEmpty() && ((ArrayList<?>) javaObject).get(0) instanceof Data) {
                ServiceManager.getInstance().dataService.saveAll((ArrayList<Data>) javaObject);
            }
        }
    }

    private void startDevice(Message message, Device newDevice) {
        LOGGER.info("Registering and logged in the device " + newDevice.getDeviceName() + ".");

        // If already exists a old device with the same deviceName the new device, the _id will be copied from
        // old to new device. This will ensure that the new device will be update instead of created in the
        // database.
        final Device oldDevice = ServiceManager.getInstance().deviceService.findByDeviceName(newDevice.getDeviceName());
        if (oldDevice != null) {
            newDevice.set_id(oldDevice.get_id());
            LOGGER.info(
                    "Device " + newDevice.getDeviceName() + " was already registered. This Device will be logged in.");
        }

        newDevice.setGatewayUUID(message.getGatewayId().toString());
        newDevice.setUUID(message.getSenderId().toString());
        ServiceManager.getInstance().deviceService.save(newDevice);

        // Responding that device registration was successful.
        LOGGER.info("Device " + newDevice.getDeviceName() + " ready.");
        sendMessage(message.getGatewayId(), message.getSenderId(), ConnectionState.ONLINE);
    }

    private void delegateAction(Action newAction) {
        ServiceManager.getInstance().actionService.save(newAction);

        // Discovering the device that must perform this action.
        final Device deviceByCommand = ServiceManager.getInstance().deviceService.findByCommandId(
                newAction.getCommandId());
        LOGGER.info("Delagating command to " + deviceByCommand.getDeviceName() + ".");
        UUID gatewayId = UUID.fromString(deviceByCommand.getGatewayUUID());
        UUID receiverId = UUID.fromString(deviceByCommand.getUUID());
        sendMessage(gatewayId, receiverId, newAction);
    }

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
