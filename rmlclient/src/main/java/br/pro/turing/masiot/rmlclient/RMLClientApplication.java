package br.pro.turing.masiot.rmlclient;

import br.pro.turing.masiot.core.model.Action;
import br.pro.turing.masiot.core.model.Command;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.model.Resource;
import br.pro.turing.masiot.core.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class RMLClientApplication {

    public static void main(String[] args) {
        String gatewayIP = "127.0.0.1";
        int gatewayPort = 5500;
        if (args.length == 2) {
            gatewayIP = args[0];
            try {
                gatewayPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                LOGGER.severe("Was not possible to start the RML client because the \"port\" argument is out of number "
                        + "format.");
                System.exit(SYSTEM_EXIT_NUMBER_FORMAT_ARG_ERROR);
            }
        } else if (args.length == 0) {
            LOGGER.info("The RML client will use the default ContextNet gateway IP and port.");
        } else {
            LOGGER.severe("Invalid arguments for the RML client.");
            LOGGER.info("For start program with customized gateway, use the follow: gatewayIP gatewayPort. For start "
                    + "program with default gateway, don't use none argument.");
            System.exit(SYSTEM_EXIT_INVALID_ARG_ERROR);
        }

        final RMLClient rmlClient = new RMLClient(extractDevice()) {

            Random random = new Random();

            @Override
            protected String getValue(Resource resource) {
                return String.valueOf(this.random.nextDouble());
            }

            @Override
            protected void onAction(Action action) {
            }
        };
        rmlClient.connect(gatewayIP, gatewayPort);
    }

    private static final Logger LOGGER = LoggerUtils.initLogger(RMLClientApplication.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/masiot/rmlclient/rmlclient.logging.properties"),
            RMLClientApplication.class.getSimpleName());

    private static final int SYSTEM_EXIT_NUMBER_FORMAT_ARG_ERROR = 1;

    private static final int SYSTEM_EXIT_INVALID_ARG_ERROR = 2;

    private static Device extractDevice() {
        List<Resource> resources = new ArrayList<>();

        resources.add(new Resource("resource1", "Resource 1", "This is the resource 1 without commands", "COM3", 1000));

        List<Command> commands = new ArrayList<>();
        commands.add(new Command("on", "Turns on the resource2"));
        commands.add(new Command("ff", "Turns off the resource2"));
        resources.add(new Resource("resource2", "Resource 2", "This is the resource 2 with 2 commands", "COM3", 1000,
                commands));

        return new Device("device1", "Device 1", "This device simulates equipment that will interact with RML",
                resources);
    }
}
