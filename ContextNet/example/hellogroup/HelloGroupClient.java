package hellogroup;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.groups.Group;
import lac.cnclib.net.groups.GroupCommunicationManager;
import lac.cnclib.net.groups.GroupMembershipListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.logging.Logger;

public class HelloGroupClient implements NodeConnectionListener, GroupMembershipListener {

    private static final Logger LOGGER = Logger.getLogger(HelloGroupClient.class.getSimpleName());
    private static String gatewayIP = "127.0.0.1";
    private static int gatewayPort = 5500;
    private GroupCommunicationManager groupManager;
    private Group group;

    public HelloGroupClient() {
        InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
        try {
            MrUdpNodeConnection connection = new MrUdpNodeConnection();
            connection.connect(address);
            connection.addNodeConnectionListener(this);

            group = new Group(250, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HelloGroupClient();
    }

    @Override
    public void connected(NodeConnection nodeConnection) {
        groupManager = new GroupCommunicationManager(nodeConnection);
        groupManager.addMembershipListener(this);
        try {
            groupManager.joinGroup(group);
            ApplicationMessage message = new ApplicationMessage();
            message.setContentObject("Group Message");
            groupManager.sendGroupcastMessage(message, group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {

    }

    @Override
    public void disconnected(NodeConnection nodeConnection) {

    }

    @Override
    public void newMessageReceived(NodeConnection nodeConnection, Message message) {
        System.out.println("Group sender also received: " + message.getContentObject());
    }

    @Override
    public void unsentMessages(NodeConnection nodeConnection, List<Message> list) {

    }

    @Override
    public void internalException(NodeConnection nodeConnection, Exception e) {

    }

    @Override
    public void enteringGroups(List<Group> list) {
        System.out.println("Entered in the group");
    }

    @Override
    public void leavingGroups(List<Group> list) {

    }
}
