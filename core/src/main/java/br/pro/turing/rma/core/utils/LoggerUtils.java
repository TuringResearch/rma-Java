package br.pro.turing.rma.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Logger utility.
 */
public class LoggerUtils {

    /**
     * Starts the logger.
     *
     * @param stream     Input steam of the configuration file.
     * @param simpleName Name of the class that contain the logger.
     * @return Logger.
     */
    public static Logger initLogger(InputStream stream, String simpleName) {
        readLoggingPropertiesFile(stream);
        return Logger.getLogger(simpleName);
    }

    /**
     * Read configuration from the file to build the logger.
     *
     * @param stream Input steam of the configuration file.
     */
    private static void readLoggingPropertiesFile(InputStream stream) {
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
