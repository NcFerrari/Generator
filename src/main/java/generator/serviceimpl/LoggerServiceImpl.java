package generator.serviceimpl;

import generator.service.LoggerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerServiceImpl implements LoggerService {

    private static LoggerService loggerService;
    private static Logger log;

    /**
     * Singleton basic method
     *
     * @param classLogging Class from which one this method is called. Don't forget .class suffix
     * @return LoggerServiceImpl what is object of this interface
     */
    public static <T> LoggerService getInstance(Class<T> classLogging) {
        if (loggerService == null) {
            loggerService = new LoggerServiceImpl();
        }

        log = LogManager.getLogger(classLogging);
        return loggerService;
    }

    /**
     * Singleton basic implementation
     */
    private LoggerServiceImpl() {
    }

    /**
     * For logging, you can use methods like:
     * <ul>
     *     <li>trace()</li>
     *     <li>debug()</li>
     *     <li>info()</li>
     *     <li>warn()</li>
     *     <li>error()</li>
     *     <li>fatal()</li>
     * </ul>
     *
     * @return Logger for print message into output
     */
    @Override
    public Logger getLog() {
        return log;
    }
}
