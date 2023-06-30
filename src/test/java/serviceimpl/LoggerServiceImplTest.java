package serviceimpl;

import generator.serviceimpl.LoggerServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import generator.service.LoggerService;

class LoggerServiceImplTest {

    private LoggerService loggerService = LoggerServiceImpl.getInstance(LoggerServiceImplTest.class);
    private Logger log = loggerService.getLog();

    @Test
    void getLog() {
        Logger.getRootLogger().setLevel(Level.TRACE);
        logging();
        Logger.getRootLogger().setLevel(Level.DEBUG);
        logging();
        Logger.getRootLogger().setLevel(Level.INFO);
        logging();
        Logger.getRootLogger().setLevel(Level.WARN);
        logging();
        Logger.getRootLogger().setLevel(Level.ERROR);
        logging();
    }

    private void logging() {
        log.info("======================");
        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
        log.info("======================");
    }

    @Test
    void test() {
        int i = 0;
        while (i < 3) {
            i++;
            LoggerService loggerService = LoggerServiceImpl.getInstance(LoggerServiceImplTest.class);
            Logger log = loggerService.getLog();
            log.info("huga čaga");
        }
    }
}