package module.compile;

import bin.apply.Setting;
import bin.exception.FileException;
import module.compile.item.FileSave;

import java.io.*;
import java.util.Locale;

public class DecompileTest {
    String separator = ".otli";
    public void start(File file) {
        if (file.getName().toLowerCase(Locale.ROOT).endsWith(separator)) {
            FileSave fileSave = input(file.getAbsolutePath());
            Setting.mainPath = file.getAbsolutePath();
            Setting.path = file.getParentFile().getAbsolutePath();
        } else throw new FileException().rightExtension();
    }

    private FileSave input(String fileName) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            if (input.readObject() instanceof FileSave fileSave) return fileSave;
            else throw new FileException().noReadError();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileException().noFindError();
        }
    }
}
