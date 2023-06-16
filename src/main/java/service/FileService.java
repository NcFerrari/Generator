package service;

import java.io.InputStream;
import java.util.List;

public interface FileService {

    String[] loadData(InputStream resourceAsStream);

    void createAndFillJavaFile(String fileName, String filePath, String content);

    List<String> getFileNames(String path);

}
