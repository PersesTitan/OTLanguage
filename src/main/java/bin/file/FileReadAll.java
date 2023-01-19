package bin.file;

import bin.exception.FileException;
import org.apache.parquet.Files;
import work.v3.ReturnWorkV3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Map;

import static bin.apply.sys.item.Separator.SEPARATOR_LINE;

public class FileReadAll extends ReturnWorkV3 {
    // 1 = file path
    public FileReadAll() {
        super(1);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        StringBuilder total = new StringBuilder();
        File file = new File(params[0]);
        try {
            Files.readAllLines(file, StandardCharsets.UTF_8)
                    .forEach(v -> total.append(SEPARATOR_LINE).append(v));
        } catch (IOException e) {
            throw new FileException().noFindError();
        }
        return total.toString();
    }
}
