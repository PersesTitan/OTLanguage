package bin.apply.sys.item;

import bin.exception.VariableException;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static bin.apply.Repository.*;

public interface SystemSetting {
    String[] extension = {".otl", ".otlanguage"};

    static boolean extensionCheck(String fileName) {
        fileName = fileName.toLowerCase(Locale.ROOT);
        return Arrays.stream(extension)
                .anyMatch(fileName::endsWith);
    }

    static String getExtension() {
        return String.join(", ", extension);
    }

    // 동작을 생성하는 로직
    static void createStartWorks(Map<String, Map<String, StartWorkV3>> map,
                                 String klassName, String methodName, StartWorkV3 startWork) {
        if (map.containsKey(klassName)) {
            if (map.get(klassName).containsKey(methodName)) throw new VariableException().sameVariable();
            else map.get(klassName).put(methodName, startWork);
        } else map.put(klassName, new HashMap<>() {{ put(methodName, startWork); }});
    }

    static void createReturnWorks(Map<String, Map<String, ReturnWorkV3>> map,
                                  String klassName, String methodName, ReturnWorkV3 returnWork) {
        if (map.containsKey(klassName)) {
            if (map.get(klassName).containsKey(methodName)) throw new VariableException().sameVariable();
            else map.get(klassName).put(methodName, returnWork);
        } else map.put(klassName, new HashMap<>() {{ put(methodName, returnWork); }});
    }

    static void createStartWorks(String klassName, String methodName, StartWorkV3 startWork) {
        createStartWorks(startWorksV3, klassName, methodName, startWork);
    }

    static void priorityCreateStartWorks(String klassName, String methodName, StartWorkV3 startWork) {
        createStartWorks(priorityStartWorksV3, klassName, methodName, startWork);
    }

    static void createReturnWorks(String klassName, String methodName, ReturnWorkV3 returnWork) {
        createReturnWorks(returnWorksV3, klassName, methodName, returnWork);
    }
}
