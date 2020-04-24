package br.pro.turing.masiot.core.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerUtils {
    public LoggerUtils() {
    }

    public static Logger initLogger(InputStream stream, String simpleName) {
        initLoggingPropertiesFile(stream);
        return Logger.getLogger(simpleName);
    }

    private static void initLoggingPropertiesFile(String filePath) {
        try {
            FileInputStream loggingConfigFile = new FileInputStream(filePath);
            LogManager.getLogManager().readConfiguration(loggingConfigFile);
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    private static void initLoggingPropertiesFile(InputStream stream) {
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
