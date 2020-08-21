package br.pro.turing.rma.applicationlayer;

import br.pro.turing.rma.core.model.Action;
import br.pro.turing.rma.core.model.Command;
import br.pro.turing.rma.core.model.Device;
import br.pro.turing.rma.core.model.Resource;
import br.pro.turing.rma.core.service.ServiceManager;
import br.pro.turing.rma.core.utils.LoggerUtils;
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

/**
 * The bridge of the application layer between the cliente and the RML.
 */
public class RMLBridge implements NodeConnectionListener {

    /** Logger. */
    private static final Logger LOGGER = LoggerUtils.initLogger(RMLBridge.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/rma/applicationlayer/applicationlayer.logging.properties"),
            RMLBridge.class.getSimpleName());

    /** IoT by ContextNet connection instance. */
    private MrUdpNodeConnection connection;

    /** IP socket address of the ContextNet gateway. */
    private InetSocketAddress gatewayAddress;

    /**
     * Connects to the RML.
     *
     * @param gatewayIP   Gateway IP.
     * @param gatewayPort Gateway port.
     */
    public void connect(String gatewayIP, int gatewayPort) {
        LOGGER.info("Connecting this client to RML.");
        this.gatewayAddress = new InetSocketAddress(gatewayIP, gatewayPort);
        try {
            connection = new MrUdpNodeConnection();
            connection.addNodeConnectionListener(this);
            connection.connect(gatewayAddress);
        } catch (IOException e) {
            LOGGER.severe("I/O exception error while try to connect this client to RML.");
        }
    }

    /**
     * Create an action in a instant of time to perform a command.
     *
     * @param localDateTime Instant time.
     * @param device
     * @param resource
     * @param command       Command to be performed.
     */
    public void createAction(LocalDateTime localDateTime, Device device, Resource resource, Command command) {
        Action action = new Action(localDateTime, device.getDeviceName(), resource.getResourceName(),
                command.getCommand());
        Message message = new ApplicationMessage();
        final String jason = ServiceManager.getInstance().jsonService.toJson(action);
        message.setContentObject(jason);

        try {
            connection.sendMessage(message);
        } catch (IOException e) {
            LOGGER.severe("I/O error while trying to send a message when this client connects with RML");
        }
    }

    /**
     * Informs that this Application layer is connected to RML server.
     *
     * @param nodeConnection Node connection.
     */
    @Override
    public void connected(NodeConnection nodeConnection) {
        LOGGER.info("Client connected.");
    }

    /**
     * Informs that this Application layer is reconnected to TML server.
     *
     * @param nodeConnection Node connection.
     * @param socketAddress  Socket address.
     * @param b              B
     * @param b1             B1
     */
    @Override
    public void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {
        LOGGER.info("Client reconnected.");
    }

    /**
     * Informs that this Application layer is disconnected from RML server.
     *
     * @param nodeConnection Node connection.
     */
    @Override
    public void disconnected(NodeConnection nodeConnection) {
        LOGGER.info("Client disconnected.");
    }

    /**
     * Informs that some messages was not sent. If this Application layer is connected to RML server, the unsert
     * messages will be resent.
     *
     * @param nodeConnection Node connection.
     * @param list           Unsent messages list.
     */
    @Override
    public void unsentMessages(NodeConnection nodeConnection, List<Message> list) {
        StringBuilder errorMessageLog = new StringBuilder();
        errorMessageLog.append("Unsent mesage(s):");
        list.forEach(message -> errorMessageLog.append("\n")
                .append(Serialization.fromJavaByteStream(message.getContent()).toString()));
        LOGGER.severe(errorMessageLog.toString());
        LOGGER.info("Resending messages");
        for (Message message : list) {
            try {
                connection.sendMessage(message);
            } catch (IOException e) {
                LOGGER.severe("I/O error while trying to send a message when this client connects with RML");
                return;
            }
        }
    }

    @Override
    public void newMessageReceived(NodeConnection nodeConnection, Message message) {

    }

    @Override
    public void internalException(NodeConnection nodeConnection, Exception e) {

    }
}
