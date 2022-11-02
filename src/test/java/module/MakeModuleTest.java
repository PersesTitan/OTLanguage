package module;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class MakeModuleTest<T> {
    public void make(String path, T t) {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
