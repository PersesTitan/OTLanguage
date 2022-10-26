package bin.apply;

import bin.v3.math.console.Scanner;
import bin.v3.math.random.*;
import bin.v3.variable.Variable;
import work.ReturnWork;
import work.StartWork;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.*;

import static bin.apply.Controller.scanner;
import static bin.check.VariableCheck.*;
import static bin.check.VariableCheck.isLong;
import static bin.token.ConsoleToken.SCANNER;
import static bin.token.cal.NumberToken.*;

public interface Repository {
    Map<String, Map<String, Object>> repository = new HashMap<>();
    Set<String> noUse = new HashSet<>();

    List<ReturnWork> returnWorks = new ArrayList<>();
    List<StartWork> startWorks = new ArrayList<>();
    List<StartWork> priorityWorks = new ArrayList<>();
    Map<String, StartWork> startWorkMap = new HashMap<>();
    Map<String, StartWork> priorityWorkMap = new HashMap<>();

    static Set<String> getSet(Map<String, Map<String, Object>> repository) {
        Set<String> set = new HashSet<>(noUse);
        repository.forEach((k, v) -> set.addAll(v.keySet()));
        return set;
    }

    // Version 3
    Variable variable = new Variable();
    Map<String, Map<String, StartWorkV3>> startWorksV3 = new HashMap<>();
    Map<String, Map<String, ReturnWorkV3>> returnWorksV3 = new HashMap<>();

    default void createStartWorks(String klassName, String methodName, StartWorkV3 startWork) {
        if (startWorksV3.containsKey(klassName)) startWorksV3.get(klassName).put(methodName, startWork);
        else startWorksV3.put(klassName, new HashMap<>() {{ put(methodName, startWork); }});
    }

    default void createReturnWorks(String klassName, String methodName, ReturnWorkV3 returnWork) {
        if (returnWorksV3.containsKey(klassName)) returnWorksV3.get(klassName).put(methodName, returnWork);
        else returnWorksV3.put(klassName, new HashMap<>() {{ put(methodName, returnWork); }});
    }

    default void first() {
        createReturnWorks(RANDOM_BOOL, "", new RandomBoolean(1));
        createReturnWorks(RANDOM_DOUBLE, "", new RandomDouble(1, 2));
        createReturnWorks(RANDOM_FLOAT, "", new RandomFloat(1, 2));
        createReturnWorks(RANDOM_INTEGER, "", new RandomInteger(1, 2));
        createReturnWorks(RANDOM_LONG, "", new RandomLong(1, 2));
        createReturnWorks(SCANNER, "", new Scanner(0));
        returnWorksV3
                .values()
                .forEach(v -> v.values().forEach(a -> makeWork(a.getClass().getSimpleName(), a)));
    }

    private void makeWork(String path, ReturnWorkV3 work) {
        path = "v3Test/" + path + ".otlm";
        try (ObjectOutput output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(work);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
