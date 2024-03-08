package serviceimpl;

import generator.serviceimpl.LoggerServiceImpl;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import generator.service.LoggerService;

class LoggerServiceImplTest {

    private LoggerService loggerService = LoggerServiceImpl.getInstance(LoggerServiceImplTest.class);
    private Logger log = loggerService.getLog();

    @Test
    void getLog() {
        LogManager.getRootLogger().atTrace();
        logging();
        LogManager.getRootLogger().atDebug();
        logging();
        LogManager.getRootLogger().atInfo();
        logging();
        LogManager.getRootLogger().atWarn();
        logging();
        LogManager.getRootLogger().atError();
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