package bin.file;

import bin.exception.FileException;
import org.apache.parquet.Files;
import work.v3.ReturnWorkV3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Map;

public class ReadAllListFile extends ReturnWorkV3 {
    // 1 = file path
    public ReadAllListFile() {
        super(1);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        File file = new File(params[0]);
        try {
            return Files.readAllLines(file, StandardCharsets.UTF_8).toString();
        } catch (IOException e) {
            throw new FileException().noFindError();
        }
    }
}
