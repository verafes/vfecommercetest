package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tests.BaseTest;

public class LoggerUtils {
    public static final String ERROR = "❌";
    public static final String SUCCESS = "✅";
    public static final String WARNING = "⚠️";
    private static final String EXCEPTION = "❗";

    private static final Logger logger = LogManager.getLogger(BaseTest.class.getSimpleName());

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(String message) {
        logger.error(ERROR + message);
    }

    public static void logFatal(String message) {
        logger.fatal(EXCEPTION + ERROR + WARNING + message);
    }

    public static void logWarning(String message) {
        logger.warn(WARNING + message);
    }
}
