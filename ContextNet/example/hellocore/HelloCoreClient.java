package hellocore;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.logging.Logger;

public class HelloCoreClient implements NodeConnectionListener {

    private static final Logger LOGGER = Logger.getLogger(HelloCoreClient.class.getSimpleName());
    private static String gatewayIP = "127.0.0.1";
    private static int gatewayPort = 5500;
    private MrUdpNodeConnection connection;
    private String name;

    public static void main(String[] args) {
        new HelloCoreClient(args[0]);
    }

    public HelloCoreClient(String name) {
        this.name = name;
        InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
        try {
            connection = new MrUdpNodeConnection();
            connection.addNodeConnectionListener(this);
            connection.connect(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connected(NodeConnection nodeConnection) {
        ApplicationMessage message = new ApplicationMessage();
        String serializableContent = "I'm " + name;
        message.setContentObject(serializableContent);

        try {
            connection.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newMessageReceived(NodeConnection nodeConnection, Message message) {
        System.out.println("From Server to Client: " + message.getContentObject());
    }

    public void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {

    }

    public void disconnected(NodeConnection nodeConnection) {

    }

    public void unsentMessages(NodeConnection nodeConnection, List<Message> list) {

    }

    public void internalException(NodeConnection nodeConnection, Exception e) {

    }
}
