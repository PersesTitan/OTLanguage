package work.setting;

import bin.apply.Setting;
import bin.exception.FileException;
import work.ReturnWork;
import work.v3.StartWorkV3;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static bin.apply.sys.item.Separator.*;

public class ReadModule<T> {
    public T read(String path) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(path))) {
            return (T) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileException().addModuleError();
        }
    }
}
