package serviceimpl;

import generator.serviceimpl.FileServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import generator.service.FileService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FileServiceImplTest {

    private static FileService fileService;

    @BeforeAll
    static void start() {
        fileService = FileServiceImpl.getInstance();
    }

    @Test
    void loadFile() {
        String[] names = fileService.loadData(
                FileServiceImplTest.class.getClassLoader().getResourceAsStream(("sources/names.txt")));
        assertTrue(Arrays.asList(names).contains("Lubor"));
    }
}