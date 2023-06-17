import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import service.LoggerService;
import serviceimpl.LoggerServiceImpl;

class JPACreatorTest {
    private final LoggerService loggerService = LoggerServiceImpl.getInstance(JPACreatorTest.class);
    private final Logger log = loggerService.getLog();
    private final JPACreator jpaCreator = new JPACreator("bf2stats");
    private final String testingTable = "servers";
    private final String testingTable2 = "player";

    @Test
    void entityGeneratorTest() {

    }
}