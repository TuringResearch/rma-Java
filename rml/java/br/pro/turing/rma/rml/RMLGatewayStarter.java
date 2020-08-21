package br.pro.turing.rma.rml;

import br.pro.turing.rma.core.utils.LoggerUtils;
import lac.cnet.gateway.components.Gateway;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Resource Management Layer gateway starter. This classe is responsible to rise up all gateways passed as argument.
 */
public class RMLGatewayStarter {

    /** Logger. */
    private static final Logger LOGGER = LoggerUtils.initLogger(RMLGatewayStarter.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/rma/rml/rml.logging.properties"),
            RMLGatewayStarter.class.getSimpleName());

    /** Error code that is returned when a invalid gateway is passed. */
    private static final int GATEWAY_INVALID_ARGS = 2;

    /** Error code that is returned when a native code load problem occurs. */
    private static final int SYSTEM_EXIT_NATIVE_CODE_LIBRARY_FAILED = 4;

    /** All gateway address found in the argument. */
    public final List<InetSocketAddress> gatewayAddress;

    /**
     * Constructor.
     *
     * @param gateways Gateway addresses.
     */
    public RMLGatewayStarter(String[] gateways) {
        this.gatewayAddress = getGatewayAddress(gateways);
    }

    /**
     * Returns the gateway addresses list by a gateway addresses array.
     * @param gateways The gateway array.
     * @return The gateway addresses list.
     */
    public List<InetSocketAddress> getGatewayAddress(String[] gateways) {
        List<InetSocketAddress> gatewayAddress = new ArrayList<>();
        if (gateways.length % 2 == 0) {
            for (int i = 0; i < gateways.length; i = i + 2) {
                int portIndex = i + 1;
                try {
                    gatewayAddress.add(new InetSocketAddress(gateways[i], Integer.parseInt(gateways[portIndex])));
                } catch (NumberFormatException e) {
                    LOGGER.severe("The value '" + gateways[portIndex] + "' must be a Integer value.");
                    System.exit(GATEWAY_INVALID_ARGS);
                }
            }
        } else {
            LOGGER.severe(
                    "Incomplete arguments. You can put many gateway address. For each, is necessary put IP and port.");
            System.exit(GATEWAY_INVALID_ARGS);
        }
        return gatewayAddress;
    }

    /**
     * Starts all gateways detected.
     */
    public void start() {
        LOGGER.info("Starting " + this.gatewayAddress.size() + " gateways...");
        String newLibPath = System.getProperty("user.dir");
        String libPath = System.getProperty("java.library.path");
        libPath = libPath + ";" + newLibPath;
        System.setProperty("java.library.path", libPath);

        try {
            System.load(newLibPath + "\\" + "sigar-amd64-winnt.dll");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(SYSTEM_EXIT_NATIVE_CODE_LIBRARY_FAILED);
        }

        for (InetSocketAddress address : this.gatewayAddress) {
            String strDDSVendor = "OpenSplice";
            UniversalDDSLayerFactory.SupportedDDSVendors ddsVendor = UniversalDDSLayerFactory
                    .convertStrToSupportedDDSVendor(strDDSVendor);
            UUID id = UUID.randomUUID();
            int gatewayNumber = this.gatewayAddress.indexOf(address) + 1;
            try {
                new Gateway(address.getPort(), address.getHostString(), id, false, ddsVendor);
                LOGGER.info(gatewayNumber + "º Gateway MR-UDP (IP " + address.getHostString() + ":" + address.getPort()
                        + ") started.");
            } catch (IOException var5) {
                LOGGER.severe(
                        gatewayNumber + "º Gateway MR-UDP (IP " + address.getHostString() + ":" + address.getPort()
                                + ") NOT started.");
            }
        }
    }
}
