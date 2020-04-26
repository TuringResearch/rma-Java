package br.pro.turing.masiot.rml;

import br.pro.turing.masiot.core.utils.LoggerUtils;
import lac.cnet.gateway.components.Gateway;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class RMLGatewayStarter {

    private static final Logger LOGGER = LoggerUtils.initLogger(RMLGatewayStarter.class.getClassLoader()
                    .getResourceAsStream("br/pro/turing/masiot/rml/rml.logging.properties"),
            RMLGatewayStarter.class.getSimpleName());

    private static final int GATEWAY_INVALID_ARGS = 2;

    public final List<InetSocketAddress> gatewayAddress;

    public RMLGatewayStarter(String[] gateways) {
        this.gatewayAddress = getGatewayAddress(gateways);
    }

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
            System.exit(1);
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
