package etc.module;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import static bin.apply.sys.item.Separator.SEPARATOR_FILE;

public class MakeModuleTest<T> {
    // 경로, 파일명, 확장자
    public void make(String path, String fileName, String exi, T t) {
        path = (path.endsWith(SEPARATOR_FILE)
                ? path
                : path + SEPARATOR_FILE) + fileName + exi;
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
