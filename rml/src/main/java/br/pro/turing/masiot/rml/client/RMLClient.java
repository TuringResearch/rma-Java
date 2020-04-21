package br.pro.turing.masiot.rml.client;

import br.pro.turing.masiot.core.model.Command;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RMLClient implements NodeConnectionListener {

    private static final Logger LOGGER = Logger.getLogger(RMLClient.class.getName());

    private MrUdpNodeConnection connection;

    private Device device;

    public RMLClient(Device device) {
        this.device = device;
    }

    public void connect(String gatewayIP, int gatewayPort) {
        LOGGER.info("Connecting this device (" + device.getDeviceName() + ") to RML.");
        InetSocketAddress gatewayAddress = new InetSocketAddress(gatewayIP, gatewayPort);
        try {
            connection = new MrUdpNodeConnection();
            connection.addNodeConnectionListener(this);
            connection.connect(gatewayAddress);
            LOGGER.info("Device (" + device.getDeviceName() + ") connected.");
        } catch (IOException e) {
            LOGGER.severe("I/O exception error while try to connect this device to RML.");
        }
    }

    @Override
    public void connected(NodeConnection nodeConnection) {
        LOGGER.info("Logging in or registering this device (" + device.getDeviceName() + ") in RML.");
        Message message = new ApplicationMessage();
        message.setContentObject(this.device);
        try {
            connection.sendMessage(message);
        } catch (IOException e) {
            LOGGER.severe("I/O error while trying to send a message when this client connects with RML");
        }
    }

    @Override
    public void newMessageReceived(NodeConnection nodeConnection, Message message) {
        System.out.println("From Server to Client: " + message.getContentObject());
    }

    @Override
    public void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {

    }

    @Override
    public void disconnected(NodeConnection nodeConnection) {

    }

    @Override
    public void unsentMessages(NodeConnection nodeConnection, List<Message> list) {

    }

    @Override
    public void internalException(NodeConnection nodeConnection, Exception e) {

    }
}
