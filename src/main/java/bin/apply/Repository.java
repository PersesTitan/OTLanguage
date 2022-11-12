package bin.apply;

import bin.exception.VariableException;
import bin.orign.variable.StartVariable;
import bin.orign.variable.Variable;
import work.ReturnWork;
import work.StartWork;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.io.*;
import java.util.*;

import static bin.apply.sys.item.Separator.*;
import static bin.token.LoopToken.*;
import static bin.token.cal.BoolToken.*;

public interface Repository {
    LinkedList<Map<String, Map<String, Object>>> repository = new LinkedList<>();
    Set<String> noUse = new HashSet<>();
    List<String> noContains = Arrays.asList(TOKEN, OR, AND, NO_TOKEN);

    List<ReturnWork> returnWorks = new ArrayList<>();
    List<StartWork> startWorks = new ArrayList<>();

    static boolean containsVariable(String variable, Map<String, Map<String, Object>> repository) {
        return TOTAL_LIST.stream().anyMatch(v -> repository.get(v).containsKey(variable));
    }

    // Version 3
    Variable variable = new Variable(1);
    StartVariable startVariable = new StartVariable(2);
    HashMap<String, Map<String, StartWorkV3>> priorityStartWorksV3 = new HashMap<>();
    HashMap<String, Map<String, StartWorkV3>> startWorksV3 = new HashMap<>() {{put(VAR_TOKEN, new HashMap<>());}};
    HashMap<String, Map<String, ReturnWorkV3>> returnWorksV3 = new HashMap<>() {{put(VAR_TOKEN, new HashMap<>());}};

    static void createStartWorks(Map<String, Map<String, StartWorkV3>> map,
                                  String klassName, String methodName, StartWorkV3 startWork) {
        if (map.containsKey(klassName)) {
            if (map.get(klassName).containsKey(methodName)) throw new VariableException().sameVariable();
            else map.get(klassName).put(methodName, startWork);
        } else map.put(klassName, new HashMap<>() {{ put(methodName, startWork); }});
    }

    static void createStartWorks(String klassName, String methodName, StartWorkV3 startWork) {
        createStartWorks(startWorksV3, klassName, methodName, startWork);
    }

    static void priorityCreateStartWorks(String klassName, String methodName, StartWorkV3 startWork) {
        createStartWorks(priorityStartWorksV3, klassName, methodName, startWork);
    }

    static void createReturnWorks(Map<String, Map<String, ReturnWorkV3>> map,
                              String klassName, String methodName, ReturnWorkV3 returnWork) {
        if (map.containsKey(klassName)) {
            if (map.get(klassName).containsKey(methodName)) throw new VariableException().sameVariable();
            else map.get(klassName).put(methodName, returnWork);
        } else map.put(klassName, new HashMap<>() {{ put(methodName, returnWork); }});
    }

    static void createReturnWorks(String klassName, String methodName, ReturnWorkV3 returnWork) {
        createReturnWorks(returnWorksV3, klassName, methodName, returnWork);
    }

    private static HashMap<String, Map<String, StartWorkV3>> readStart(File file) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(file))) {
            return (HashMap<String, Map<String, StartWorkV3>>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Setting.warringMessage(String.format("%s를 모듈에 추가하지 못하였습니다.", file.getName()));
            return null;
        }
    }
}
