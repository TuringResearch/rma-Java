package br.pro.turing.masiot.rmlclient;

import br.pro.turing.masiot.core.model.*;
import javafx.beans.property.SimpleObjectProperty;
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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class RMLClient implements NodeConnectionListener {

    private static final Logger LOGGER = Logger.getLogger(RMLClient.class.getName());

    private MrUdpNodeConnection connection;

    private Device device;

    private ConnectionState connectionState = ConnectionState.OFFLINE;

    private InetSocketAddress gatewayAddress;

    private SimpleObjectProperty<Action> action;

    public RMLClient(Device device) {
        this.device = device;
        this.action = new SimpleObjectProperty<>();
        this.startCycle();
    }

    public void connect(String gatewayIP, int gatewayPort) {
        LOGGER.info("Connecting this device (" + device.getDeviceName() + ") to RML.");
        this.gatewayAddress = new InetSocketAddress(gatewayIP, gatewayPort);
        try {
            connection = new MrUdpNodeConnection();
            connection.addNodeConnectionListener(this);
            connection.connect(gatewayAddress);
            LOGGER.info("Device (" + device.getDeviceName() + ") connected.");
        } catch (IOException e) {
            LOGGER.severe("I/O exception error while try to connect this device to RML.");
        }
    }

    public ArrayList<Data> buildDataBuffer() {
        ArrayList<Data> dataArrayList = new ArrayList<>();
        this.device.getResourceList().forEach(resource -> {
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                String value = String.valueOf(random.nextDouble());
                dataArrayList.add(new Data(LocalDateTime.now(ZoneId.systemDefault()), resource.get_id(), value));
            }
        });

        return dataArrayList;
    }

    private void startCycle() {
        new Thread(() -> {
            LOGGER.info("Starting RML client cycle.");
            while (true) {
                if (this.connectionState.equals(ConnectionState.ONLINE)) {
                    final ArrayList<Data> dataList = buildDataBuffer();
                    if (dataList != null && !dataList.isEmpty()) {
                        LOGGER.fine("Sending data...");
                        Message message = new ApplicationMessage();
                        message.setContentObject(dataList);
                        try {
                            connection.sendMessage(message);
                        } catch (IOException e) {
                            LOGGER.severe("I/O error while trying to send a message when this client connects with RML");
                        }
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
        Object messageReceived = Serialization.fromJavaByteStream(message.getContent());
        if (messageReceived instanceof ConnectionState) {
            ConnectionState connectionState = (ConnectionState) messageReceived;
            if (connectionState.equals(ConnectionState.ONLINE)) {
                LOGGER.info("This device (" + this.device.getDeviceName() + ") is online on RML.");
                this.connectionState = connectionState;
            }
        } else if (messageReceived instanceof Action && this.connectionState.equals(ConnectionState.ONLINE)) {
            Action action = (Action) messageReceived;
            LOGGER.info("A new action was request: " + action.toString());
            this.action.set(action);
        }
    }

    @Override
    public void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {
        this.connectionState = ConnectionState.ONLINE;
        LOGGER.warning("This device (" + this.device.getDeviceName() + ") was reconnected");
    }

    @Override
    public void disconnected(NodeConnection nodeConnection) {
        this.connectionState = ConnectionState.OFFLINE;
        LOGGER.severe("This device (" + this.device.getDeviceName() + ") was disconnected.");
    }

    @Override
    public void unsentMessages(NodeConnection nodeConnection, List<Message> list) {
        StringBuilder errorMessageLog = new StringBuilder();
        errorMessageLog.append("Unsent mesage(s):");
        list.forEach(message -> errorMessageLog.append("\n").append(Serialization.fromJavaByteStream(message.getContent()).toString()));
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
    public void internalException(NodeConnection nodeConnection, Exception e) {

    }
}
