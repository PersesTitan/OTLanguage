package bin.apply.sys.compile;

import bin.apply.sys.compile.items.CompileItem;
import bin.apply.sys.compile.items.CountMap;
import bin.apply.sys.compile.tool.FindModel;
import bin.apply.sys.item.DebugMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static bin.apply.Controller.println;
import static bin.apply.Repository.*;
import static bin.apply.Setting.debugMode;

public class Compile {
    public Compile(File file) {
        // 모드 변경
        debugMode = DebugMode.COMPILE;
        // 사용하는 모듈 저장하는 저장소 생성
        Set<String> usedKlass = new HashSet<>();
        Set<String> usedModel = new HashSet<>();
        Map<String, Set<String>> moduleSet = new FindModel().getModuleSet();

        replaceMap(priorityStartWorksV3, usedKlass);
        replaceMap(startWorksV3, usedKlass);
        replaceMap(returnWorksV3, usedKlass);

        println("Compile check print...");
        println("======================");
        CompileItem save = new CompileItem(file, usedModel);
        println("======================");
        println("Compile check finish");

        usedKlass.forEach(v -> {
            for (Map.Entry<String, Set<String>> map : moduleSet.entrySet()) {
                if (map.getValue().contains(v)) usedModel.add(map.getKey());
            }
        });

        output(save, file.getName() + "c");
    }

    private <V> void replaceMap(Map<String, Map<String, V>> map, Set<String> usedKlass) {
        var set = new HashSet<>(map.keySet());
        for (var key : set) {
            var m = map.remove(key);
            map.put(key, new CountMap<>(m, key, usedKlass));
        }
    }

    private void output(Object o, String fileName) {
        try (var output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
