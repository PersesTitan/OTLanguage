package work.setting;

import bin.apply.Repository;
import bin.apply.Setting;
import bin.apply.sys.item.Color;
import bin.token.MergeToken;
import work.ReturnWork;
import work.StartWork;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static bin.apply.sys.item.Separator.SEPARATOR_FILE;

// Setting : .otls
// Model : .otlm
public class ReadOTLM implements Repository, MergeToken {
    public static final String modelPath = ".otlm";
    public static final String compulsion = "compulsion";
    public static final String alteration = "alteration";
    public static final String operate = "operate";

    public static void main(String[] args) {
        new ReadOTLM().readSetting("system.otls");
    }

    // compulsion : 강제 실행
    // alteration : 변경
    // operate : 작동
    public void readSetting(String filePath) {
        try {
            Files.readAllLines(Path.of(filePath))
                    .stream()
                    .filter(Predicate.not(Objects::isNull))
                    .filter(Predicate.not(String::isBlank))
                    .map(String::strip)
                    .forEach(v -> {
                        if (v.endsWith(":")) mode.set(bothEndCut(v, 0, 1));
                        else readOTLM(v);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final AtomicReference<String> mode = new AtomicReference<>();
    private void readOTLM(String path) {
        String modeType = mode.get();
        String ph = "module" + SEPARATOR_FILE + mode + SEPARATOR_FILE + path + modelPath;
        if (Objects.equals(modeType, operate)) {
            var work = readStartWork(path, ph);
            if (work != null) startWorks.add(work);
        } else if (Objects.equals(modeType, compulsion)) {
            var work = readStartWork(path, ph);
//            if (work != null) priorityWorks.add(work);
        } else if (Objects.equals(modeType, alteration)) {
            var work = readReturnWork(path, ph);
            if (work != null) returnWorks.add(work);
        }
    }

    private ReturnWork readReturnWork(String path, String getPath) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(getPath))) {
            return (ReturnWork) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Setting.warringMessage(String.format("%s를 모듈에 추가하지 못하였습니다.", path + modelPath));
            return null;
        }
    }

    private StartWork readStartWork(String path, String getPath) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(getPath))) {
            return (StartWork) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Setting.warringMessage(String.format("%s를 모듈에 추가하지 못하였습니다.", path + modelPath));
            return null;
        }
    }
}
