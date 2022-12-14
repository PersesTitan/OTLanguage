package module.download;

import bin.apply.sys.item.Separator;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bin.apply.sys.item.Separator.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public interface CreateGitDir {
    default void makeDir(String path) {
        File file = new File(path).getParentFile();
        if (file.isDirectory()) return;
        file.mkdirs();
    }

    default void makeDir(File file) {
        delete(file);
        makeDir(file.getAbsolutePath());
    }

    default void delete(File file) {
        file.delete();
    }


    default void copy(File file1, File klassName) throws IOException {
        Files.copy(file1.toPath(), klassName.toPath(), REPLACE_EXISTING);
    }

    // br = 파일 쓰기
    // name = 모듈 이름(ex.origin, poison)
    // filePath = src/main/java/origin
    default List<File> copyFiles(BufferedWriter br, String... filePath) throws IOException {
        List<File> files = new ArrayList<>();
        for (String fileP : filePath) {
            fileP = fileP.substring("src/main/java/".length());
            File startFile = new File("out/production/classes/" + fileP);
            getFile(files, startFile);
        }

        List<String> list = files.stream()
                .map(File::toString)
                .map(v -> v.substring("out/production/classes/".length()))
                .map(v -> v.replace("/", "~"))
                .toList();
        // 파일 라인 값 추가하기
        for (String line : list) {
            br.newLine();
            br.write("   ");
            br.write(line);
        }
        return files;
    }

    default void getFile(List<File> list, File file) {
        File[] files = file.listFiles(v -> !v.getName().equals("cos"));
        if (files == null) return;
        for (File f : files) {
            if (f.isFile()) list.add(f);
            else getFile(list, f);
        }
    }

    /**
     * @param type operate
     * @param name origin
     * @param bool true (is origin)
     */
    default <V> void createModule(HashMap<String, Map<String, V>> repository, BufferedWriter br, MakeGitTest.Type type, String name,
                                  boolean bool, String namePath, String namePath2) throws IOException {
        if (!repository.isEmpty()) {
            createModule(getPath(namePath, type.getName() + MODULE_EXTENSION), repository);
            createModule(getPath(MODULE_PATH, type.getName(), name + MODULE_EXTENSION), repository);
            if (bool) createModule(getPath(namePath2, type.getName(), name + MODULE_EXTENSION), repository);

            br.write("   ");
            br.write(type.getName());
            br.newLine();
        }
    }

    enum Type {
        COMPULSION(Separator.COMPULSION),   // 강제
        ALTERATION(Separator.ALTERATION),   // 변경
        OPERATE(Separator.OPERATE);         // 동작

        private final String name;
        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    default <V> void createModule(String path, HashMap<String, Map<String, V>> repository) {
        makeDir(path);
        try (var output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(repository);
        } catch (IOException ignored) {}
    }
}
