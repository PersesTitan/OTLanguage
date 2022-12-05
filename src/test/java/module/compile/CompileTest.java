package module.compile;

import bin.apply.Repository;
import bin.apply.Setting;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import module.compile.item.FileSave;

import java.io.*;

import static bin.apply.sys.item.Separator.EXT_REP;

public class CompileTest {
    public static void main(String[] args) {
//        new MakeGitTest();
//        new Setting();

//        new CompileTest(new File("web.otl"));
        FileSave fileSave = input("item.otli");

        Setting.mainPath = new File("web.otl").getAbsolutePath();
        Setting.path = new File("web.otl").getAbsoluteFile().getParent();

        assert fileSave != null;
        StartLine.startStartLine(fileSave.getFinalTotal(), fileSave.getTotal(), Repository.repository);
    }

    private CompileTest(File file) {
        FileSave save = new FileSave(file);
        output(save);
    }

    private void output(Object o) {
        try (var output = new ObjectOutputStream(new FileOutputStream("item.otli"))) {
            output.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileSave input(String fileName) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(fileName))) {
            if (input.readObject() instanceof FileSave fileSave) return fileSave;
            else throw new FileException().noReadError();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileException().noFindError();
        }
    }
}
