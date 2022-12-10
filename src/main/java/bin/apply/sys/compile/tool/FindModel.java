package bin.apply.sys.compile.tool;

import bin.apply.Setting;

import java.io.*;
import java.util.*;

import static bin.apply.sys.item.Separator.*;
import static bin.apply.sys.item.Separator.MODULE_EXTENSION;

public class FindModel {
    public Map<String, Set<String>> getModuleSet() {
        Map<String, Set<String>> list = new HashMap<>();

        final String path =  MODULE_PATH + SEPARATOR_FILE;
        final File[] file1 = new File(path + COMPULSION).listFiles(this::checkModel);
        final File[] file2 = new File(path + OPERATE).listFiles(this::checkModel);
        final File[] file3 = new File(path + ALTERATION).listFiles(this::checkModel);
        if (file1 != null) for (File files : file1) readStart(files, list);
        if (file2 != null) for (File files : file2) readStart(files, list);
        if (file3 != null) for (File files : file3) readStart(files, list);

        return list;
    }

    private boolean checkModel(File file) {
        return file.getName().toLowerCase(Locale.ROOT).endsWith(MODULE_EXTENSION);
    }

    private <V> void readStart(File file, Map<String, Set<String>> list) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(file))) {
            var map = (HashMap<String, Map<String, V>>) input.readObject();
            map.forEach((k, v) -> list.computeIfAbsent(file.getName(), a -> createSet()).add(k));
        } catch (IOException | ClassNotFoundException ignored) {
            Setting.warringMessage(String.format("%s를 모듈에 추가하지 못하였습니다.", file.getName()));
        }
    }

    private Set<String> createSet() {
        return new HashSet<>();
    }
}