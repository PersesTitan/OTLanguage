package bin.apply;

import bin.exception.VariableException;
import bin.v3.console.*;
import bin.v3.console.Scanner;
import bin.v3.console.priority.PriorityPrint;
import bin.v3.console.priority.PriorityPrintSpace;
import bin.v3.console.priority.PriorityPrintTap;
import bin.v3.console.priority.PriorityPrintln;
import bin.v3.math.random.*;
import bin.v3.string.*;
import bin.v3.sys.ForceQuit;
import bin.v3.variable.StartVariable;
import bin.v3.variable.Variable;
import bin.v3.variable.create.CreateList;
import bin.v3.variable.create.CreateMap;
import bin.v3.variable.create.CreateOrigin;
import bin.v3.variable.create.CreateSet;
import cos.http.controller.HttpMethod;
import cos.poison.v3.PoisonCreate;
import cos.poison.v3.PoisonMethod;
import cos.poison.v3.PoisonStart;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import work.ReturnWork;
import work.StartWork;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.*;

import static bin.token.ConsoleToken.*;
import static bin.token.LoopToken.*;
import static bin.token.StringToken.*;
import static bin.token.VariableToken.*;
import static bin.token.cal.NumberToken.*;

public interface Repository {
    LinkedList<Map<String, Map<String, Object>>> repository = new LinkedList<>();
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

    static boolean containsVariable(String variable, Map<String, Map<String, Object>> repository) {
        return TOTAL_LIST.stream().anyMatch(v -> repository.get(v).containsKey(variable));
    }

    // Version 3
    Variable variable = new Variable(1);
    StartVariable startVariable = new StartVariable(2);
    Map<String, Map<String, StartWorkV3>> priorityStartWorksV3 = new HashMap<>();
    Map<String, Map<String, StartWorkV3>> startWorksV3 = new HashMap<>() {{put(VAR_TOKEN, new HashMap<>());}};
    Map<String, Map<String, ReturnWorkV3>> returnWorksV3 = new HashMap<>() {{put(VAR_TOKEN, new HashMap<>());}};

    default void createStartWorks(Map<String, Map<String, StartWorkV3>> map,
                                  String klassName, String methodName, StartWorkV3 startWork) {
        if (map.containsKey(klassName)) {
            if (map.get(klassName).containsKey(methodName)) throw VariableException.sameVariable();
            else map.get(klassName).put(methodName, startWork);
        } else map.put(klassName, new HashMap<>() {{ put(methodName, startWork); }});
    }

    default void createStartWorks(String klassName, String methodName, StartWorkV3 startWork) {
        createStartWorks(startWorksV3, klassName, methodName, startWork);
    }

    default void priorityCreateStartWorks(String klassName, String methodName, StartWorkV3 startWork) {
        createStartWorks(priorityStartWorksV3, klassName, methodName, startWork);
    }

    default void createReturnWorks(Map<String, Map<String, ReturnWorkV3>> map,
                              String klassName, String methodName, ReturnWorkV3 returnWork) {
        if (map.containsKey(klassName)) {
            if (map.get(klassName).containsKey(methodName)) throw VariableException.sameVariable();
            else map.get(klassName).put(methodName, returnWork);
        } else map.put(klassName, new HashMap<>() {{ put(methodName, returnWork); }});
    }

    default void createReturnWorks(String klassName, String methodName, ReturnWorkV3 returnWork) {
        createReturnWorks(returnWorksV3, klassName, methodName, returnWork);
    }

    default void first() {
        priorityCreateStartWorks(FORCE_QUIT, "", new ForceQuit(0));
        priorityCreateStartWorks(PRIORITY_PRINT, "", new PriorityPrint(1));
        priorityCreateStartWorks(PRIORITY_PRINTLN, "", new PriorityPrintln(1));
        priorityCreateStartWorks(PRIORITY_PRINT_TAP, "", new PriorityPrintTap(1));
        priorityCreateStartWorks(PRIORITY_PRINT_SPACE, "", new PriorityPrintSpace(1));

        createReturnWorks(RANDOM_BOOL, "", new RandomBoolean(1));
        createReturnWorks(RANDOM_DOUBLE, "", new RandomDouble(1, 2));
        createReturnWorks(RANDOM_FLOAT, "", new RandomFloat(1, 2));
        createReturnWorks(RANDOM_INTEGER, "", new RandomInteger(1, 2));
        createReturnWorks(RANDOM_LONG, "", new RandomLong(1, 2));
        createReturnWorks(SCANNER, "", new Scanner(0));

        CreateOrigin createOrigin = new CreateOrigin();
        CreateSet createSet = new CreateSet();
        CreateList createList = new CreateList();
        CreateMap createMap = new CreateMap();
        ORIGIN_LIST.forEach(v -> createStartWorks(v, "", createOrigin));
        SET_LIST.forEach(v -> createStartWorks(v, "", createSet));
        LIST_LIST.forEach(v -> createStartWorks(v, "", createList));
        MAP_LIST.forEach(v -> createStartWorks(v, "", createMap));
        createStartWorks(PRINT, "", new Print(1));
        createStartWorks(PRINTLN, "", new Println(1));
        createStartWorks(PRINT_SPACE, "", new PrintSpace(1));
        createStartWorks(PRINT_TAP, "", new PrintTap(1));

        createStartWorks(POISON, "", new PoisonCreate(1, 2));
        createStartWorks(POISON, POISON_START, new PoisonStart(0));
        createStartWorks(POISON, POISON_POST, new PoisonMethod(HttpMethod.POST));
        createStartWorks(POISON, POISON_GET, new PoisonMethod(HttpMethod.GET));

        createReturnWorks(STRING_VARIABLE, JOIN, new Join(2));
        createReturnWorks(STRING_VARIABLE, SPLIT, new Split(2));
        createReturnWorks(STRING_VARIABLE, SPLIT_REGULAR, new SplitRegular(2));
        createReturnWorks(STRING_VARIABLE, CONTAINS.replace("\\", ""), new Contains(2));
        createReturnWorks(STRING_VARIABLE, EQUALS.replace("\\", ""), new Equals(2));
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
