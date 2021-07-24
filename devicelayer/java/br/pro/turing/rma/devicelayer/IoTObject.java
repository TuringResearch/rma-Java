package br.pro.turing.rma.devicelayer;

import br.pro.turing.rma.core.model.Action;
import br.pro.turing.rma.core.model.Data;
import br.pro.turing.rma.core.model.Device;
import br.pro.turing.rma.core.model.Resource;
import br.pro.turing.rma.core.service.ServiceManager;
import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;
import lac.cnclib.sddl.serialization.Serialization;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * IoT Object is a device able of (i) connecting and registering in the RML when it starts. It is initially configured
 * to be part of a specific environment, and it informs all of its available resources; (ii) it gathers data from all
 * its resources, and it sends them to the RML and; (iii) it receives from the RML actions that must be executed by
 * the device's actuators.
 */
public abstract class IoTObject implements NodeConnectionListener {

    private Logger logger = Logger.getLogger("IoTObject");

    /** Splitter value for microcontrollers buffer to separate timestamp and measures. */
    protected static final String SPLIT_TIME = "    ";

    /** Splitter value for microcontrollers buffer to separate the measures. */
    protected static final String SPLIT_VALUE = ";";

    /** Tamanho máximo da mensagem em byte. */
    private static final int MAX_MESSAGE_BYTE = 2000;

    /** Timestamp format for miclocontroller buffer. */
    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss.SSS");

    /** IoT by ContextNet connection instance. */
    private MrUdpNodeConnection connection;

    /** Model of IoT objects. */
    private Device device;

    /** IP socket address of the ContextNet gateway. */
    private InetSocketAddress gatewayAddress;

    /** State of connection. */
    private boolean connected = false;

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
    public static Device buildDeviceByConfigFile(String deviceConfigurationFilePath) throws FileNotFoundException {
        Reader jsonFile = new FileReader(deviceConfigurationFilePath);
        return ServiceManager.getInstance().jsonService.fromJson(jsonFile, Device.class);
    }

    /**
     * Search by an UUID if exists.
     *
     * @return UUID or empty text.
     */
    private String getUUIDFromFile() throws IOException {
        File file = new File(".device");
        if (!file.exists()) {
            return "";
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final String uuid;
            uuid = bufferedReader.readLine();
            return uuid;
        } finally {
            inputStream.close();
        }
    }

    /**
     * Create a file if not exists and write a new UUID value.
     *
     * @param uuid UUID.
     * @throws IOException
     */
    private void setUUIDToFile(String uuid) throws IOException {
        File file = new File(".device");
        if (!file.exists()) {
            Files.createFile(file.toPath());
        }
        FileWriter writer = new FileWriter(".device");
        writer.write(uuid);
        writer.close();
    }

    /**
     * Connects to the RML.
     *
     * @param gatewayIP   Gateway IP.
     * @param gatewayPort Gateway port.
     */
    public void connect(String gatewayIP, int gatewayPort) throws IOException {
        this.gatewayAddress = new InetSocketAddress(gatewayIP, gatewayPort);
        final String uuid = this.getUUIDFromFile();
        connection = uuid.isEmpty() ? new MrUdpNodeConnection() : new MrUdpNodeConnection(UUID.fromString(uuid));
        connection.addNodeConnectionListener(this);

        connection.connect(gatewayAddress);
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
            while (true) {
                final long t1 = System.currentTimeMillis();
                logger.info(";in;" + System.nanoTime());
                if (this.connected) {
                    final ArrayList<Data> dataList = buildDataBuffer();
                    if (!dataList.isEmpty()) {
                        Message message = new ApplicationMessage();
                        message.setContentObject(ServiceManager.getInstance().jsonService.toJson(dataList));
                        try {
                            connection.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                logger.info(";out;" + System.nanoTime());
                final long duration = System.currentTimeMillis() - t1;
                try {
                    Thread.sleep(duration < this.device.getCycleDelayInMillis() ?
                                 this.device.getCycleDelayInMillis() - duration : 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
            dataList.add(new Data(time, this.device.getDeviceName(), resource.getResourceName(), value));
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
        new Thread(() -> {
            String msg = ServiceManager.getInstance().jsonService.toJson(this.device);
            Message message = new ApplicationMessage();
            message.setContentObject(msg);
            try {
                nodeConnection.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            // TODO Log here for the TIME CONNECTION SEND
            this.connected = true;
        }).start();
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
        String messageReceived = (String) Serialization.fromJavaByteStream(message.getContent());
        if (ServiceManager.getInstance().jsonService.jasonIsObject(messageReceived, Device.class.getName())) {
            // TODO Log here for the TIME CONNECTION RECEIVE
            this.device = ServiceManager.getInstance().jsonService.fromJson(messageReceived, Device.class);
            try {
                if (this.getUUIDFromFile().isEmpty()) {
                    this.setUUIDToFile(this.device.getUUID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (ServiceManager.getInstance().jsonService.jasonIsObject(messageReceived, Action.class.getName())) {
            Action action = ServiceManager.getInstance().jsonService.fromJson(messageReceived, Action.class);
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
        this.connected = true;
    }

    /**
     * Informs that this IoT Object is disconnected from RML server.
     *
     * @param nodeConnection Node connection.
     */
    @Override
    public final void disconnected(NodeConnection nodeConnection) {
        this.connected = false;
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
        for (Message message : list) {
            try {
                connection.sendMessage(message);
            } catch (IOException e) {
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
}
