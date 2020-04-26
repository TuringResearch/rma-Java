package br.pro.turing.masiot.iostobject;

import br.pro.turing.masiot.core.model.*;
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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class IoSTObject implements NodeConnectionListener {

    protected static final String SPLIT_TIME = "    ";

    protected static final String SPLIT_VALUE = ";";

    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSS");

    private static final Logger LOGGER = LoggerUtils.initLogger(IoSTObject.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/masiot/iostobject/iostobject.logging.properties"),
            IoSTObject.class.getSimpleName());

    private MrUdpNodeConnection connection;

    private Device device;

    private int cycleDelay;

    private ConnectionState connectionState = ConnectionState.OFFLINE;

    private InetSocketAddress gatewayAddress;

    public IoSTObject(Device device, int cycleDelay) {
        this.device = device;
        this.cycleDelay = cycleDelay;
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

    protected abstract void onAction(Action action);

    protected abstract ArrayList<Data> buildDataBuffer();

    private void startCycle() {
        new Thread(() -> {
            LOGGER.info("Starting RML client cycle.");
            while (true) {
                final long t1 = System.currentTimeMillis();
                if (this.connectionState.equals(ConnectionState.ONLINE)) {
                    final ArrayList<Data> dataList = buildDataBuffer();
                    if (!dataList.isEmpty()) {
                        LOGGER.fine("Sending data...");
                        Message message = new ApplicationMessage();
                        message.setContentObject(dataList);
                        try {
                            connection.sendMessage(message);
                        } catch (IOException e) {
                            LOGGER.severe(
                                    "I/O error while trying to send a message when this client connects with RML");
                        }
                    }
                }
                final long duration = System.currentTimeMillis() - t1;
                try {
                    Thread.sleep(duration < this.cycleDelay ? this.cycleDelay - duration : 0);
                } catch (InterruptedException e) {
                    LOGGER.severe(e.getMessage());
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
    public final void newMessageReceived(NodeConnection nodeConnection, Message message) {
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
            this.onAction(action);
        }
    }

    @Override
    public final void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {
        this.connectionState = ConnectionState.ONLINE;
        LOGGER.warning("This device (" + this.device.getDeviceName() + ") was reconnected");
    }

    @Override
    public final void disconnected(NodeConnection nodeConnection) {
        this.connectionState = ConnectionState.OFFLINE;
        LOGGER.severe("This device (" + this.device.getDeviceName() + ") was disconnected.");
    }

    @Override
    public final void unsentMessages(NodeConnection nodeConnection, List<Message> list) {
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
    public final void internalException(NodeConnection nodeConnection, Exception e) {

    }

    /**
     * @return {@link #device}
     */
    public Device getDevice() {
        return this.device;
    }

    /**
     * @return {@link #cycleDelay}
     */
    public int getCycleDelay() {
        return this.cycleDelay;
    }

    protected ArrayList<Data> extractValue(Resource resource, String buffer) {
        ArrayList<Data> dataList = new ArrayList<>();
        String[] bufferArray = buffer.split(SPLIT_TIME);
        LocalDateTime time = LocalDateTime.parse(bufferArray[0], DATE_TIME_FORMATTER);
        String[] valuesArray = bufferArray[1].split(SPLIT_VALUE);
        for (String value : valuesArray) {
            dataList.add(new Data(time, resource.get_id(), value));
            time = time.plus(resource.getWaitTimeInMillis(), ChronoUnit.MILLIS);
        }
        return dataList;
    }
}
