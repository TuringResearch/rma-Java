package br.pro.turing.masiot.iostobject.actionclient;

import br.pro.turing.masiot.core.model.Action;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.service.ServiceManager;
import br.pro.turing.masiot.core.utils.LoggerUtils;
import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;
import lac.cnclib.sddl.serialization.Serialization;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class RMLActionClientApplication implements NodeConnectionListener {

    private static final Logger LOGGER = LoggerUtils.initLogger(RMLActionClientApplication.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/masiot/iostobject/iostobject.logging.properties"),
            RMLActionClientApplication.class.getSimpleName());

    private static String gatewayIP = "127.0.0.1";

    private static int gatewayPort = 5500;

    private MrUdpNodeConnection connection;

    public static void main(String[] args) {
        new RMLActionClientApplication();
    }

    public RMLActionClientApplication() {
        InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
        try {
            connection = new MrUdpNodeConnection();
            connection.addNodeConnectionListener(this);
            connection.connect(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Device device = ServiceManager.getInstance().deviceService.findByDeviceName("device1");
        final Resource resource = device.getResourceList().stream().filter(
                resource1 -> !resource1.getCommandList().isEmpty()).findFirst().orElse(null);

        new Thread(() -> {
            int count = 0;

            while (true) {
                Action action = new Action(LocalDateTime.now(), resource.getCommandList().get(count).get_id());
                ApplicationMessage message = new ApplicationMessage();
                message.setContentObject(action);
                count = (count == resource.getCommandList().size() - 1) ? 0 : count + 1;

                try {
                    connection.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void connected(NodeConnection nodeConnection) {
        LOGGER.info("RML Action Client connected.");
    }

    public void newMessageReceived(NodeConnection nodeConnection, Message message) {
        Object messageReceived = Serialization.fromJavaByteStream(message.getContent());
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
