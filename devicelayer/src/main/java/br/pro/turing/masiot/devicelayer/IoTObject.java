package br.pro.turing.masiot.devicelayer;

import br.pro.turing.masiot.core.model.*;
import br.pro.turing.masiot.core.utils.LoggerUtils;
import com.google.gson.Gson;
import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;
import lac.cnclib.sddl.serialization.Serialization;
import org.bson.types.ObjectId;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * IoT Object is a device able of (i) connecting and registering in the RML when it starts. It is initially configured
 * to be part of a specific environment, and it informs all of its available resources; (ii) it gathers data from all
 * its resources, and it sends them to the RML and; (iii) it receives from the RML actions that must be executed by
 * the device's actuators.
 */
public abstract class IoTObject implements NodeConnectionListener {

    /** Splitter value for microcontrollers buffer to separate timestamp and measures. */
    protected static final String SPLIT_TIME = "    ";

    /** Splitter value for microcontrollers buffer to separate the measures. */
    protected static final String SPLIT_VALUE = ";";

    /** Timestamp format for miclocontroller buffer. */
    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSS");

    /** Logger. */
    private static final Logger LOGGER = LoggerUtils.initLogger(IoTObject.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/masiot/devicelayer/devicelayer.logging.properties"),
            IoTObject.class.getSimpleName());

    /** IoT by ContextNet connection instance. */
    private MrUdpNodeConnection connection;

    /** Model of IoT objects. */
    private Device device;

    /** IoT Object connection state. If Object is logged in to RML, it is online. */
    private ConnectionState connectionState = ConnectionState.OFFLINE;

    /** IP socket address of the ContextNet gateway. */
    private InetSocketAddress gatewayAddress;

    /**
     * Constructor.
     *
     * @param device {@link #device}
     */
    public IoTObject(Device device) {
        this.device = device;
        this.startCycle();
    }

    /**
     * Builds a device given a device configuration file.
     *
     * @param deviceConfigurationFilePath Device configuration file.
     * @return Device.
     */
    public static Device buildDeviceByConfigFile(String deviceConfigurationFilePath) {
        Device newDevice = null;
        try (Reader jsonFile = new FileReader(deviceConfigurationFilePath)) {
            newDevice = new Gson().fromJson(jsonFile, Device.class);
            newDevice.set_id(new ObjectId());
            newDevice.getResourceList().forEach(resource -> {
                resource.set_id(new ObjectId());
                resource.getCommandList().forEach(command -> command.set_id(new ObjectId()));
            });
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        return newDevice;
    }

    /**
     * Connects to the RML.
     *
     * @param gatewayIP   Gateway IP.
     * @param gatewayPort Gateway port.
     */
    public void connect(String gatewayIP, int gatewayPort) {
        LOGGER.info("Connecting this device (" + device.getDeviceName() + ") to RML.");
        this.gatewayAddress = new InetSocketAddress(gatewayIP, gatewayPort);
        try {
            connection = new MrUdpNodeConnection();
            connection.addNodeConnectionListener(this);
            connection.connect(gatewayAddress);
        } catch (IOException e) {
            LOGGER.severe("I/O exception error while try to connect this device to RML.");
        }
    }

    /**
     * Forwards the action to the microcontroller to make.
     *
     * @param action Action to be performed.
     */
    protected abstract void onAction(Action action);

    /**
     * Builds the data buffer from the microcontroller to be persisted in the RML.
     *
     * @return Data list.
     */
    protected abstract ArrayList<Data> buildDataBuffer();

    /**
     * Starts the IoT Object cycle. This cycle is responsible for synchronizing the IoT Object's activities of sending
     * data and executing actions.
     */
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
                    Thread.sleep(duration < this.device.getCycleDelayInMillis() ?
                                 this.device.getCycleDelayInMillis() - duration : 0);
                } catch (InterruptedException e) {
                    LOGGER.severe(e.getMessage());
                }
            }
        }).start();
    }

    /**
     * Transform a String data buffet into a Data List. The format of the buffer must be:
     * yyyy-MM-dd HH:mm:ss.SSS    V1;V2;V3;...;VN
     *
     * @param resource Owner resource of the message.
     * @param buffer   Data buffer.
     * @return Data list.
     */
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

    /**
     * Informs that this IoT Object is connected to RML server. Once connected, this Object will send a Device
     * instance to be registered (if not exists on RML) or logged in (if this device is already registered).
     *
     * @param nodeConnection Node connection.
     */
    @Override
    public void connected(NodeConnection nodeConnection) {
        LOGGER.info("Device (" + device.getDeviceName() + ") connected.");
        LOGGER.info("Logging in or registering this device (" + device.getDeviceName() + ") in RML.");
        Message message = new ApplicationMessage();
        message.setContentObject(this.device);
        try {
            connection.sendMessage(message);
        } catch (IOException e) {
            LOGGER.severe("I/O error while trying to send a message when this client connects with RML");
        }
    }

    /**
     * Menage incoming messages from RML. If the message is about connection state, it means that the Device was
     * registered or logged in RML. If message is about Action, a new action will be performed in some resource of
     * this device.
     *
     * @param nodeConnection Node cnnection
     * @param message        Message.
     */
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

    /**
     * Informs that this IoT Object is reconnected to TML server.
     *
     * @param nodeConnection Node connection.
     * @param socketAddress  Socket address.
     * @param b              B
     * @param b1             B1
     */
    @Override
    public final void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {
        this.connectionState = ConnectionState.ONLINE;
        LOGGER.warning("This device (" + this.device.getDeviceName() + ") was reconnected");
    }

    /**
     * Informs that this IoT Object is disconnected from RML server.
     *
     * @param nodeConnection Node connection.
     */
    @Override
    public final void disconnected(NodeConnection nodeConnection) {
        this.connectionState = ConnectionState.OFFLINE;
        LOGGER.severe("This device (" + this.device.getDeviceName() + ") was disconnected.");
    }

    /**
     * Informs that some messages was not sent. If this Application layer is connected to RML server, the unsert
     * messages will be resent.
     *
     * @param nodeConnection Node connection.
     * @param list           Unsent messages list.
     */
    @Override
    public final void unsentMessages(NodeConnection nodeConnection, List<Message> list) {
        StringBuilder errorMessageLog = new StringBuilder();
        errorMessageLog.append("Unsent mesage(s):");
        list.forEach(message -> errorMessageLog.append("\n")
                .append(Serialization.fromJavaByteStream(message.getContent()).toString()));
        LOGGER.severe(errorMessageLog.toString());
        if (this.connectionState.equals(ConnectionState.ONLINE)) {
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
}
