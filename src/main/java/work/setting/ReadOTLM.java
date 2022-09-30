package work.setting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

// Setting : .otls
// Model : .otlm
public class ReadOTLM {
    private final static String modelPath = ".otlm";

    public static void main(String[] args) {
        new ReadOTLM().readSetting("system.otls");
    }

    // compulsion : 강제 실행
    // alteration : 변경
    // operate : 작동
    public void readSetting(String filePath) {
        String compulsion = "compulsion:";
        String alteration = "alteration:";
        String operate = "operate:";
        try {
            List<LinkedList<String>> lists = new ArrayList<>();
            var file = Files.readAllLines(Path.of(filePath))
                    .stream()
                    .filter(Predicate.not(Objects::isNull))
                    .filter(Predicate.not(String::isBlank))
                    .map(String::strip)
                    .toList();

            LinkedList<String> list = new LinkedList<>();
            for (String line : file) {
                if (line.endsWith(":")) {
                    list = new LinkedList<>();
                    lists.add(list);
                }
                list.add(line);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readOTLM(LinkedList<String> list, String path) {

    }
}
