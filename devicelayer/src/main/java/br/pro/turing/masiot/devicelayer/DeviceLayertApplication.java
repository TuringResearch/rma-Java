package br.pro.turing.masiot.devicelayer;

import br.pro.turing.masiot.core.model.Action;
import br.pro.turing.masiot.core.model.Data;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.utils.LoggerUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * A main class to simulate a device working as a IoT Object in the Device Layer of the Resource Management
 * Architecture (RMA).
 */
public class DeviceLayertApplication {

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
        String deviceName = "device1";
        int resourceWaitTimeMillis = 1000;
        String[] resourceNames = {"resource1", "resource2", "resource3"};

        if (args.length >= 5) {
            gatewayIP = args[0];
            try {
                gatewayPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                LOGGER.severe("Was not possible to start the RML client because the \"port\" argument is out of number "
                        + "format.");
                System.exit(SYSTEM_EXIT_NUMBER_FORMAT_ARG_ERROR);
            }
            deviceName = args[2];
            try {
                resourceWaitTimeMillis = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                LOGGER.severe(
                        "Was not possible to start the RML client because the \"resourceWaitTimeMillis\" argument is "
                                + "out of number format.");
                System.exit(SYSTEM_EXIT_NUMBER_FORMAT_ARG_ERROR);
            }
            resourceNames = Arrays.copyOfRange(args, 4, args.length);
        } else {
            LOGGER.severe("Invalid arguments for the RML client.");
            LOGGER.info("For start this program, put the follow arguments: gatewayIP gatewayPort deviceName "
                    + "resourceWaitTimeMillis resourceName1 resourceName2 resourceName3...");
            System.exit(SYSTEM_EXIT_INVALID_ARG_ERROR);
        }

        final IoTObject ioSTObject = createIoTObject(deviceName, resourceWaitTimeMillis, resourceNames);
        ioSTObject.connect(gatewayIP, gatewayPort);
    }

    /** Logger. */
    private static final Logger LOGGER = LoggerUtils.initLogger(DeviceLayertApplication.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/masiot/devicelayer/devicelayer.logging.properties"),
            DeviceLayertApplication.class.getSimpleName());

    /** Error code when the program closes due a number format exception. */
    private static final int SYSTEM_EXIT_NUMBER_FORMAT_ARG_ERROR = 1;

    /** Error code when the program closes due a invalid argument exception. */
    private static final int SYSTEM_EXIT_INVALID_ARG_ERROR = 2;

    /**
     * Create an IoT Object to communicate with RML.
     *
     * @param deviceName             Device name without spaces.
     * @param resourceWaitTimeMillis Wait time that each resource takes between a value writing and another.
     * @param resourceNames          Resource names without spaces.
     * @return The IoTObject.
     */
    private static IoTObject createIoTObject(String deviceName, int resourceWaitTimeMillis, String... resourceNames) {
        return new IoTObject(extractDevice(deviceName, resourceWaitTimeMillis, resourceNames), 5000) {
            @Override
            protected ArrayList<Data> buildDataBuffer() {
                ArrayList<Data> dataArrayList = new ArrayList<>();
                Random random = new Random();
                for (Resource resource : this.getDevice().getResourceList()) {
                    final LocalDateTime now = LocalDateTime.now();
                    String value = IoTObject.DATE_TIME_FORMATTER.format(now) + IoTObject.SPLIT_TIME;
                    for (int i = 0; i < this.getCycleDelay(); i += resource.getWaitTimeInMillis()) {
                        value += random.nextDouble() + IoTObject.SPLIT_VALUE;
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

    /**
     * Extract a simulated device.
     *
     * @param deviceName             Device name without spaces.
     * @param resourceWaitTimeMillis Wait time that each resource takes between a value writing and another.
     * @param resourceNames          Resource names without spaces.
     * @return A simulated device.
     */
    private static Device extractDevice(String deviceName, int resourceWaitTimeMillis, String... resourceNames) {
        List<Resource> resources = new ArrayList<>();
        for (String resourceName : resourceNames) {
            resources.add(new Resource(resourceName, resourceName,
                    "This is the resource " + resourceName + " from " + "device " + deviceName, "COM3",
                    resourceWaitTimeMillis));
        }

        return new Device(deviceName, deviceName, "This the device " + deviceName, resources);
    }
}
