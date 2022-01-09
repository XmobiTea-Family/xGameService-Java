package com.xmobitea.debug;

import lombok.NonNull;
import org.apache.log4j.Logger;

public final class xDebug {
    private static boolean isLogToFileEnable;
    private static boolean isLogToConsoleEnable;
    private static Logger logger;

    public static void init(boolean _isLogToFileEnable, boolean _isLogToConsoleEnable, @NonNull String logName) {
        isLogToFileEnable = _isLogToFileEnable;
        isLogToConsoleEnable = _isLogToConsoleEnable;

        //Log4j log4js = new Log4j();
        logger = Logger.getLogger(logName);
    }

    public static void log(@NonNull String message) {
        if (logger == null) return;

        if (isLogToConsoleEnable) {
            System.out.println("[DEBUG]: " + message);
        }

        if (isLogToFileEnable) {
            logger.debug(message);
        }
    }

    public static void logInfo(@NonNull String message) {
        if (logger == null) return;

        if (isLogToConsoleEnable) {
            System.out.println("[INFO]: " + message);
        }

        if (isLogToFileEnable) {
            logger.info(message);
        }
    }

    public static void logError(@NonNull String message) {
        if (logger == null) return;

        if (isLogToConsoleEnable) {
            System.out.println("[ERROR]: " + message);
        }

        if (isLogToFileEnable) {
            logger.error(message);
        }
    }

    public static void logWarning(@NonNull String message) {
        if (logger == null) return;

        if (isLogToConsoleEnable) {
            System.out.println("[WARN]: " + message);
        }

        if (isLogToFileEnable) {
            logger.warn(message);
        }
    }

    public static void logException(@NonNull Exception exception) {
        if (logger == null) return;

        var message = exception.getMessage() + '\n' + exception.getStackTrace().toString();

        if (isLogToConsoleEnable) {
            System.out.println("[FATAL]: " + message);
        }

        if (isLogToFileEnable) {
            logger.fatal(message);
        }
    }
}
