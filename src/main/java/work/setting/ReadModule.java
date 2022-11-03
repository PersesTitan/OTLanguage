package work.setting;

import bin.apply.Setting;
import bin.exception.FileException;
import work.ReturnWork;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class ReadModule<T> {
    public T read(String path) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(path))) {
            return (T) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileException().addModuleError();
        }
    }
}
