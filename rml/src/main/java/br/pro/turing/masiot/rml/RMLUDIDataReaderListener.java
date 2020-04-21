package br.pro.turing.masiot.rml;

import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.service.ServiceManager;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.objects.ApplicationObject;
import lac.cnet.sddl.objects.Message;
import lac.cnet.sddl.objects.PrivateMessage;
import lac.cnet.sddl.udi.core.SddlLayer;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;
import lac.cnet.sddl.udi.core.listener.UDIDataReaderListener;

import java.util.logging.Logger;

public class RMLUDIDataReaderListener implements UDIDataReaderListener<ApplicationObject> {

    private static final Logger LOGGER = Logger.getLogger(RMLUDIDataReaderListener.class.getName());

    private SddlLayer core;

    public RMLUDIDataReaderListener() {
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

            // If already exists a old device with the same deviceName the new device, the _id will be copied from
            // old to new device. This will ensure that the new device will be update instead of created in the
            // database.
            final Device oldDevice = ServiceManager.getInstance().deviceService.findByDeviceName(
                    newDevice.getDeviceName());
            if (oldDevice != null) {
                newDevice.set_id(oldDevice.get_id());
            }

            newDevice.setUUID(message.getSenderId().toString());
            ServiceManager.getInstance().deviceService.save(newDevice);
        }
    }
}
