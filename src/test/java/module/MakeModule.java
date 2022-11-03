package module;

import work.ReturnWork;

import java.io.*;
import java.util.List;

import static bin.apply.sys.item.Separator.*;

public class MakeModule<V> {
    // ex) cos/compulsion/Println.otlm
    private String getPath(BufferedWriter writer, String path, String kind, V work) throws IOException {
        writer.append("    ").append(work.getClass().getSimpleName());
        return MODULE_PATH + SEPARATOR_FILE         // /User/name/.otl/module/
                + path + SEPARATOR_FILE             // cos/
                + kind + SEPARATOR_FILE             // compulsion/
                + work.getClass().getSimpleName()   // Println
                + MODULE_EXTENSION;                 // .otlm
    }

    public void makeWork(String path, V work) {
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(path))) {

            output.writeObject(work);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // path: cos, kind: compulsion
    public void makeWork(BufferedWriter writer, String path, String kind, V work) {
        try (ObjectOutput output = new ObjectOutputStream(
                new FileOutputStream(getPath(writer, path, kind, work)))) {
            output.writeObject(work);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeWork(BufferedWriter writer, String path, String kind, List<V> works) {
        works.forEach(v -> makeWork(writer, path, kind, v));
    }

    @SafeVarargs
    public final void makeWork(BufferedWriter writer, String path, String kind, V... works) {
        for (V v : works) makeWork(writer, path, kind, v);
    }
}
