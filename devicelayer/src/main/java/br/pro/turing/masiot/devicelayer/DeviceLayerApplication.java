package br.pro.turing.masiot.devicelayer;

import br.pro.turing.masiot.core.model.Action;
import br.pro.turing.masiot.core.model.Data;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.utils.LoggerUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

/**
 * A main class to simulate a device working as a IoT Object in the Device Layer of the Resource Management
 * Architecture (RMA).
 */
public class DeviceLayerApplication {

    /**
     * Main method with the follow argument model:
     * <p>
     * gatewayIP gatewayPort deviceName resourceWaitTimeMillis resourceName1 resourceName2 resourceName3...
     * <p>
     * The minimum arguments quantity to be passed is five. The created device will have one resource minimally and
     * it resource will don't have any command.
     *
     * @param args gatewayIP gatewayPort deviceName resourceWaitTimeMillis resourceName1 resourceName2 resourceName3...
     */
    public static void main(String[] args) {
        String gatewayIP = "127.0.0.1";
        int gatewayPort = 5500;
        String deviceConfigurationFilePath = "deviceConfiguration.json";

        if (args.length == 3) {
            gatewayIP = args[0];
            try {
                gatewayPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                LOGGER.severe("Was not possible to start the RML client because the \"port\" argument is out of number "
                        + "format.");
                System.exit(SYSTEM_EXIT_NUMBER_FORMAT_ARG_ERROR);
            }
            deviceConfigurationFilePath = args[2];
        } else {
            LOGGER.severe("Invalid arguments for the RML client.");
            LOGGER.info("For start this program, put the follow arguments: gatewayIP gatewayPort "
                    + "deviceConfigurationFilePath");
            System.exit(SYSTEM_EXIT_INVALID_ARG_ERROR);
        }

        try {
            Device newDevice = IoTObject.buildDeviceByConfigFile(deviceConfigurationFilePath);
            final IoTObject ioSTObject = createIoTObject(newDevice);
            ioSTObject.connect(gatewayIP, gatewayPort);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            System.exit(SYSTEM_EXIT_DEVICE_CONFIG_FILE_NOT_FOUND);
        }
    }

    /** Logger. */
    private static final Logger LOGGER = LoggerUtils.initLogger(DeviceLayerApplication.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/masiot/devicelayer/devicelayer.logging.properties"),
            DeviceLayerApplication.class.getSimpleName());

    /** Error code when the program closes due a number format exception. */
    private static final int SYSTEM_EXIT_NUMBER_FORMAT_ARG_ERROR = 1;

    /** Error code when the program closes due a invalid argument exception. */
    private static final int SYSTEM_EXIT_INVALID_ARG_ERROR = 2;

    /** Error code when the program closes because the device configuration file was not found. */
    private static final int SYSTEM_EXIT_DEVICE_CONFIG_FILE_NOT_FOUND = 3;

    /**
     * Create an IoT Object to communicate with RML.
     *
     * @param device Device.
     * @return The IoTObject.
     */
    private static IoTObject createIoTObject(Device device) {
        return new IoTObject(device) {
            DecimalFormat df = new DecimalFormat("0.00");
            Random random = new Random();
            Random boolRandom = new Random();

            @Override
            protected ArrayList<Data> buildDataBuffer() {
                ArrayList<Data> dataArrayList = new ArrayList<>();
                for (Resource resource : this.getDevice().getResourceList()) {
                    final LocalDateTime now = LocalDateTime.now();
                    String value = IoTObject.DATE_TIME_FORMATTER.format(now) + IoTObject.SPLIT_TIME;
                    for (int i = 0; i < this.getDevice().getCycleDelayInMillis(); i += resource.getWaitTimeInMillis()) {
                        if (resource.getResourceName().equals("irrigador")) {
                            value += (boolRandom.nextBoolean() ? "Ligado" : "Desligado") + IoTObject.SPLIT_VALUE;
                        } else {
                            value += df.format(random.nextDouble() * 100) + IoTObject.SPLIT_VALUE;
                        }
                    }
                    value = value.substring(0, value.length() - 1);

                    dataArrayList.addAll(this.extractValue(resource, value));
                }
                return dataArrayList;
            }

            @Override
            protected void onAction(Action action) {
            }
        };
    }
}
