package hellocore;

import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.objects.ApplicationObject;
import lac.cnet.sddl.objects.Message;
import lac.cnet.sddl.objects.PrivateMessage;
import lac.cnet.sddl.udi.core.SddlLayer;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;
import lac.cnet.sddl.udi.core.listener.UDIDataReaderListener;

import java.util.logging.Logger;

public class HelloCoreServer implements UDIDataReaderListener<ApplicationObject> {

    private static final Logger LOGGER = Logger.getLogger(hellocore.HelloCoreClient.class.getName());

    /** Scalable Data Distribution Layer. */
    SddlLayer core;
    int counter;

    public static void main(String[] args) {
        new HelloCoreServer();
    }

    public HelloCoreServer() {
        core = UniversalDDSLayerFactory.getInstance();
        core.createParticipant(UniversalDDSLayerFactory.CNET_DOMAIN);

        core.createPublisher();
        core.createSubscriber();

        Object receiveMessageTopic = core.createTopic(Message.class, Message.class.getSimpleName());
        core.createDataReader(this, receiveMessageTopic);

        Object toMobileNodeTopic = core.createTopic(PrivateMessage.class, PrivateMessage.class.getSimpleName());
        core.createDataWriter(toMobileNodeTopic);

        counter = 0;

        System.out.println("=== Server Started (Listening) ===");

        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onNewData(ApplicationObject topicSample) {
        Message message = (Message) topicSample;
        System.out.println(Serialization.fromJavaByteStream(message.getContent()));

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setGatewayId(message.getGatewayId());
        privateMessage.setNodeId(message.getSenderId());

        synchronized (core) {
            counter++;
        }

        ApplicationMessage appMsg = new ApplicationMessage();
        appMsg.setContentObject(counter);
        privateMessage.setMessage(Serialization.toProtocolMessage(appMsg));

        core.writeTopic(PrivateMessage.class.getSimpleName(), privateMessage);
    }
}
