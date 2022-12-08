package module.compile;

import bin.apply.Setting;
import bin.apply.sys.item.DebugMode;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import module.compile.item.CountMap;
import module.compile.item.FileSave;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static bin.apply.Repository.*;
import static bin.apply.Setting.debugMode;

public class CompileTest {
    public static Set<String> usedModel = new HashSet<>();

    public static void main(String[] args) {
//        new MakeGitTest();
//        new Setting();
        debugMode = DebugMode.COMPILE;
        StartLine.developmentMode = true;

        new Setting();
        replaceMap(priorityStartWorksV3);
        replaceMap(startWorksV3);
        replaceMap(returnWorksV3);

        new CompileTest(new File("web.otl"));
        FileSave fileSave = input("item.otli");
        System.out.println(usedModel);

//        System.out.println(fileSave);

        Setting.mainPath = new File("web.otl").getAbsolutePath();
        Setting.path = new File("web.otl").getAbsoluteFile().getParent();

        assert fileSave != null;
//        StartLine.startStartLine(fileSave.getFinalTotal(), fileSave.getTotal(), Repository.repository);
    }

    private static <V> void replaceMap(Map<String, Map<String, V>> map) {
        var set = new HashSet<>(map.keySet());
        for (var key : set) {
            var m = map.remove(key);
            map.put(key, new CountMap<>(m, key));
        }
    }

    private CompileTest(File file) {
        FileSave save = new FileSave(file, usedModel);
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
