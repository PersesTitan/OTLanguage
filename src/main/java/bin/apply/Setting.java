package bin.apply;

import bin.apply.sys.item.Color;
import bin.apply.sys.item.DebugMode;
import bin.apply.sys.item.HpMap;
import bin.apply.sys.item.RunType;
import bin.apply.sys.run.FilePath;
import bin.apply.sys.run.ForceQuit;
import bin.apply.sys.run.Sleep;
import bin.apply.sys.run.TryCatch;
import bin.define.method.DefineMethod;
import bin.define.method.MethodReturn;
import bin.define.method.MethodVoid;
import bin.math.random.*;
import bin.math.sum.list.FloatListSum;
import bin.math.sum.list.IntegerListSum;
import bin.math.sum.list.LongListSum;
import bin.math.sum.set.FloatSetSum;
import bin.math.sum.set.IntegerSetSum;
import bin.math.sum.set.LongSetSum;
import bin.orign.variable.CreateList;
import bin.orign.variable.CreateMap;
import bin.orign.variable.CreateSet;
import bin.orign.console.*;
import bin.orign.loop.For;
import bin.orign.loop.ForEach;
import bin.orign.loop.While;
import bin.orign.variable.CreateOrigin;
import bin.orign.variable.list.get.*;
import bin.orign.variable.map.get.MapContains;
import bin.orign.variable.map.get.MapDelete;
import bin.orign.variable.map.get.MapGet;
import bin.orign.variable.map.get.MapPutAll;
import bin.orign.loop.If;
import bin.orign.variable.SetVariable;
import bin.orign.variable.origin.put.PutVariable;
import bin.orign.variable.set.get.*;
import bin.string.*;
import bin.v3.CreateReturnWorks;
import cos.poison.Poison;

import java.util.*;
import java.util.stream.Collectors;

import static bin.apply.Controller.variableDefault;
import static bin.token.ConsoleToken.*;
import static bin.token.LoopToken.*;
import static bin.token.StringToken.*;
import static bin.token.cal.NumberToken.*;

public class Setting implements Repository {
    public static final HashMap<String, Map<String, Object>> COPY_REPOSITORY = new HashMap<>();
    public static final StringBuilder total = new StringBuilder();
    // 기본 모드 = INFO
    public static DebugMode debugMode = DebugMode.INFO;
    public static RunType runType;

    public static String mainPath;
    public static String path;

    @SafeVarargs
    public static void start(String line, String errorLine,
                             Map<String, Map<String, Object>>...repositoryArray) {
        if (line.isBlank()) return;
        final String origen = line;
        final String value = new StringTokenizer(line).nextToken();

        if (priorityWorkMap.containsKey(value)) {priorityWorkMap.get(value).start(line, origen, repositoryArray); return;}

        for (var work : priorityWorks) {if (work.check(line)) {work.start(line, origen, repositoryArray); return;}}

        if (line.contains(VARIABLE_GET_S) && line.contains(VARIABLE_GET_E))
            line = lineStart(line, repositoryArray);

        line = CreateReturnWorks.start(line, Arrays.stream(repositoryArray).collect(Collectors.toCollection(LinkedList::new)));

        if (startWorkMap.containsKey(value)) {startWorkMap.get(value).start(line, origen, repositoryArray);return;}
        for (var work : startWorks) {if (work.check(line)) {work.start(line, origen, repositoryArray);return;}}

        runMessage(errorLine);
    }

    // ============================================================================== //
    @SafeVarargs
    public static String lineStart(String line, Map<String, Map<String, Object>>... repositoryArray) {
        if (variableDefault.check(line)) line = variableDefault.start(line);

//        for (var work : returnWorks) {if (work.check(line)) {line = work.start(line, repositoryArray);}}
        line = Controller.boolCalculator.start(line);

        if (variableDefault.changeCheck(line)) line = variableDefault.changeStart(line);
        return line;
    }

    // ============================================================================== //
    // 메세지 세팅
    public static void runMessage(String errorLine) {
        warringMessage(String.format("경고! %s는 실행되지 않은 라인 입니다.", errorLine));
    }

    public static void warringMessage(String message) {
        if (debugMode.ordinal() < DebugMode.WARRING.ordinal())
            System.out.printf("%s%s%s\n", Color.YELLOW, message, Color.RESET);
    }

    public static void errorMessage(String message) {
        if (debugMode.ordinal() < DebugMode.ERROR.ordinal())
            System.out.printf("%s%s%s\n", Color.RED, message, Color.RESET);
    }

    public static void firstStart() {
        reset();
        returnWorksV3.put(VAR_TOKEN, new HashMap<>());

        repository.put(METHOD, new HashMap<>());
        noUse.add(SCANNER);
        noUse.add(RANDOM_BOOL);
        noUse.add(RANDOM_DOUBLE);
        noUse.add(RANDOM_FLOAT);
        noUse.add(RANDOM_INTEGER);
        noUse.add(RANDOM_LONG);
        TOTAL_LIST.forEach(v -> COPY_REPOSITORY.put(v, new HpMap(v)));
        TOTAL_LIST.forEach(v -> repository.put(v, new HpMap(v)));

        CreateOrigin createOrigin = new CreateOrigin();
        CreateList createList = new CreateList();
        CreateSet createSet = new CreateSet();
        CreateMap createMap = new CreateMap();
        ORIGIN_LIST.forEach(v -> startWorkMap.put(v, createOrigin));
        LIST_LIST.forEach(v -> startWorkMap.put(v, createList));
        SET_LIST.forEach(v -> startWorkMap.put(v, createSet));
        MAP_LIST.forEach(v -> startWorkMap.put(v, createMap));

        priorityWorkMap.put(FORCE_QUIT, new ForceQuit());
        priorityWorkMap.put(PRIORITY_PRINT, new PriorityPrint(PRIORITY_PRINT.length()));
        priorityWorkMap.put(PRIORITY_PRINTLN, new PriorityPrintln(PRIORITY_PRINTLN.length()));
        priorityWorkMap.put(PRIORITY_PRINT_TAP, new PriorityPrintTap(PRIORITY_PRINT_TAP.length()));
        priorityWorkMap.put(PRIORITY_PRINT_SPACE, new PriorityPrintSpace(PRIORITY_PRINT_SPACE.length()));

        priorityWorks.add(new ListClear(LIST_CLEAR));
        priorityWorks.add(new ListSort(LIST_SORT));
        priorityWorks.add(new SetClear(SET_CLEAR));
        priorityWorks.add(new SetSort(SET_SORT));
        priorityWorks.add(new FilePath());

//        returnWorks.add(new SetVariable());
//        returnWorks.add(new RandomBoolean(RANDOM_BOOL));
//        returnWorks.add(new RandomDouble(RANDOM_DOUBLE));
//        returnWorks.add(new RandomFloat(RANDOM_FLOAT));
//        returnWorks.add(new RandomInteger(RANDOM_INTEGER));
//        returnWorks.add(new RandomLong(RANDOM_LONG));
//        returnWorks.add(new Input(SCANNER));
        returnWorks.add(new ListIsEmpty(LIST_ISEMPTY));
        returnWorks.add(new SetIsEmpty(SET_ISEMPTY));
        returnWorks.add(new IntegerListSum(LIST_SUM));
        returnWorks.add(new LongListSum(LIST_SUM));
        returnWorks.add(new FloatListSum(LIST_SUM));
        returnWorks.add(new IntegerSetSum(SET_SUM));
        returnWorks.add(new LongSetSum(SET_SUM));
        returnWorks.add(new FloatSetSum(SET_SUM));
        returnWorks.add(new ListGet(LIST_GET));
        returnWorks.add(new MapGet(MAP_GET));
        returnWorks.add(new SetGet(SET_GET));
        returnWorks.add(new MethodReturn());
        returnWorks.add(new Join(JOIN));
        returnWorks.add(new Split(SPLIT));
        returnWorks.add(new SplitRegular(SPLIT_REGULAR));
        returnWorks.add(new Contains(CONTAINS));
        returnWorks.add(new Equals(STRING_VARIABLE, EQUALS));
        returnWorks.add(new SetContains(SET_CONTAINS));
        returnWorks.add(new ListContains(LIST_CONTAINS));
        returnWorks.add(new MapContains(MAP_CONTAINS));

        startWorkMap.put(PRINT_SPACE, new PrintSpace(PRINT_SPACE.length()));
        startWorkMap.put(PRINT_TAP, new PrintTap(PRINT_TAP.length()));
        startWorkMap.put(PRINTLN, new Println(PRINTLN.length()));
        startWorkMap.put(PRINT, new Print(PRINT.length()));

        startWorks.add(new SetAdd(SET_ADD));
        startWorks.add(new ListAdd(LIST_ADD));
        startWorks.add(new PutVariable());
        startWorks.add(new Sleep(SLEEP));
        startWorks.add(new If(IF, ELSE_IF, ELSE));
        startWorks.add(new For());
        startWorks.add(new While(WHITE));
        startWorks.add(new ListDelete(LIST_DELETE));
        startWorks.add(new SetDelete(SET_DELETE));
        startWorks.add(new MapDelete(MAP_DELETE));
        startWorks.add(new MapPutAll(MAP_ADD));
        startWorks.add(new ForEach());
        startWorks.add(new TryCatch(TRY_CATCH));
        startWorks.add(new DefineMethod(METHOD));
        startWorks.add(new MethodVoid());
        startWorks.add(new SetReset(SET_CLEAR));

        // POISON
        startWorks.add(new Poison(POISON));
    }

    private static void reset() {
        priorityWorkMap.clear();
        startWorkMap.clear();
        returnWorks.clear();
        startWorks.clear();
        repository.clear();
        priorityWorks.clear();
        noUse.clear();
        COPY_REPOSITORY.clear();
    }
}
