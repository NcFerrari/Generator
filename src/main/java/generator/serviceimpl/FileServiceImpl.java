package generator.serviceimpl;

import org.apache.log4j.Logger;
import generator.service.FileService;
import generator.service.LoggerService;
import generator.utils.TextEnum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileServiceImpl implements FileService {

    private static FileServiceImpl fileService;
    private final LoggerService loggerService = LoggerServiceImpl.getInstance(FileServiceImpl.class);
    private final Logger log = loggerService.getLog();

    public static FileServiceImpl getInstance() {
        if (fileService == null) {
            fileService = new FileServiceImpl();
        }
        return fileService;
    }

    private FileServiceImpl() {
    }

    @Override
    public String[] loadData(InputStream resourceAsStream) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[0]);
    }

    @Override
    public void createAndFillJavaFile(String fileName, String filePath, String content) {
        if (!new File(filePath).exists()) {
            boolean success = new File(filePath).mkdirs();
            if (!success) {
                log.error(String.format(TextEnum.DIRS_NOT_CREATED.getText(), filePath));
            }
        }
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            try {
                boolean success = file.createNewFile();
                if (!success) {
                    log.error(String.format(TextEnum.FILE_NOT_CREATED.getText(), file.getName()));
                }
            } catch (IOException exp) {
                log.error(exp);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException exp) {
            log.error(exp);
        }
    }

    @Override
    public List<String> getFileNames(String path) {
        List<String> resultList = new ArrayList<>();
        File dir = new File(path);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            resultList.add(file.getName());
        }
        return resultList;
    }
}
