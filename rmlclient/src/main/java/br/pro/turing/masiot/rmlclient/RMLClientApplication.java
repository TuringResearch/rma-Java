package br.pro.turing.masiot.rmlclient;

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

public class RMLClientApplication {

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

        final RMLClient rmlClient = createRMLClient(deviceName, resourceWaitTimeMillis, resourceNames);
        rmlClient.connect(gatewayIP, gatewayPort);
    }

    private static final Logger LOGGER = LoggerUtils.initLogger(RMLClientApplication.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/masiot/rmlclient/rmlclient.logging.properties"),
            RMLClientApplication.class.getSimpleName());

    private static final int SYSTEM_EXIT_NUMBER_FORMAT_ARG_ERROR = 1;

    private static final int SYSTEM_EXIT_INVALID_ARG_ERROR = 2;

    private static RMLClient createRMLClient(String deviceName, int resourceWaitTimeMillis, String... resourceNames) {
        return new RMLClient(extractDevice(deviceName, resourceWaitTimeMillis, resourceNames), 5000) {
            @Override
            protected ArrayList<Data> buildDataBuffer() {
                ArrayList<Data> dataArrayList = new ArrayList<>();
                Random random = new Random();
                for (Resource resource : this.getDevice().getResourceList()) {
                    final LocalDateTime now = LocalDateTime.now();
                    String value = RMLClient.DATE_TIME_FORMATTER.format(now) + RMLClient.SPLIT_TIME;
                    for (int i = 0; i < this.getCycleDelay(); i += resource.getWaitTimeInMillis()) {
                        value += random.nextDouble() + RMLClient.SPLIT_VALUE;
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
