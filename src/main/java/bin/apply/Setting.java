package bin.apply;

import bin.apply.sys.item.Color;
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
import bin.orign.console.*;
import bin.orign.loop.For;
import bin.orign.loop.ForEach;
import bin.orign.loop.While;
import bin.orign.CreateOrigin;
import bin.orign.variable.list.create.*;
import bin.orign.variable.list.get.*;
import bin.orign.variable.map.create.*;
import bin.orign.variable.map.get.MapDelete;
import bin.orign.variable.map.get.MapGet;
import bin.orign.variable.map.get.MapPutAll;
import bin.orign.variable.origin.create.*;
import bin.orign.variable.set.create.*;
import bin.orign.loop.If;
import bin.orign.variable.SetVariable;
import bin.orign.variable.origin.put.PutVariable;
import bin.orign.variable.set.get.*;
import bin.string.Contains;
import bin.string.Join;
import bin.string.Split;
import bin.string.SplitRegular;
import cos.poison.Poison;
import work.StartWork;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.apply.Controller.variableDefault;
import static bin.token.ConsoleToken.*;
import static bin.token.LoopToken.*;
import static bin.token.StringToken.*;
import static bin.token.cal.NumberToken.*;

public class Setting implements Repository {
    public static final HashMap<String, Map<String, Object>> COPY_REPOSITORY = new HashMap<>();
    public static final StringBuilder total = new StringBuilder();
    public static String mainPath;
    public static String path;
    public static RunType runType;

    public static Map<String, StartWork> startWorkMap = new HashMap<>();

    @SafeVarargs
    public static void start(String line, String errorLine,
                             Map<String, Map<String, Object>>...repositoryArray) {
        if (line.isBlank()) return;
        String origen = line;

        String value = new StringTokenizer(line).nextToken();
        if (startWorkMap.containsKey(value)) {
            startWorkMap.get(value).start(line, origen, repositoryArray);
            return;
        }

        for (var work : priorityWorks) {if (work.check(line)) {work.start(line, origen, repositoryArray); return;}}
        line = lineStart(line, repositoryArray);
        for (var work : startWorks) {if (work.check(line)) {work.start(line, origen, repositoryArray);return;}}

        runMessage(errorLine);
    }

    // ============================================================================== //
    @SafeVarargs
    public static String lineStart(String line, Map<String, Map<String, Object>>... repositoryArray) {
        if (variableDefault.check(line)) line = variableDefault.start(line);

        for (var work : returnWorks) {if (work.check(line)) line = work.start(line, repositoryArray);}
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
        System.out.printf("%s%s%s\n", Color.YELLOW, message, Color.RESET);
    }

    public static void errorMessage(String message) {
        System.out.printf("%s%s%s\n", Color.RED, message, Color.RESET);
    }

    public static void firstStart() {
        reset();
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
        ORIGIN_LIST.forEach(v -> startWorkMap.put(v, createOrigin));

        priorityWorks.add(new ForceQuit(FORCE_QUIT));
        priorityWorks.add(new PriorityPrint(PRIORITY_PRINT));
        priorityWorks.add(new PriorityPrintln(PRIORITY_PRINTLN));
        priorityWorks.add(new PriorityPrintTap(PRIORITY_PRINT_TAP));
        priorityWorks.add(new PriorityPrintSpace(PRIORITY_PRINT_SPACE));
        priorityWorks.add(new ListClear(LIST_CLEAR));
        priorityWorks.add(new ListSort(LIST_SORT));
        priorityWorks.add(new SetClear(SET_CLEAR));
        priorityWorks.add(new SetSort(SET_SORT));
        priorityWorks.add(new FilePath());

        returnWorks.add(new SetVariable());
        returnWorks.add(new RandomBoolean(RANDOM_BOOL));
        returnWorks.add(new RandomDouble(RANDOM_DOUBLE));
        returnWorks.add(new RandomFloat(RANDOM_FLOAT));
        returnWorks.add(new RandomInteger(RANDOM_INTEGER));
        returnWorks.add(new RandomLong(RANDOM_LONG));
        returnWorks.add(new Input(SCANNER));
        returnWorks.add(new ListIsEmpty(LIST_ISEMPTY));
        returnWorks.add(new SetIsEmpty(SET_ISEMPTY));
        returnWorks.add(new IntegerListSum());
        returnWorks.add(new LongListSum());
        returnWorks.add(new FloatListSum());
        returnWorks.add(new IntegerSetSum());
        returnWorks.add(new LongSetSum());
        returnWorks.add(new FloatSetSum());
        returnWorks.add(new ListGet(LIST_GET));
        returnWorks.add(new MapGet(MAP_GET));
        returnWorks.add(new MethodReturn());
        returnWorks.add(new Join(JOIN));
        returnWorks.add(new Split(SPLIT));
        returnWorks.add(new SplitRegular(SPLIT_REGULAR));
        returnWorks.add(new Contains(CONTAINS));

        // ORIGEN
        startWorks.add(new CreateBoolean(BOOL_VARIABLE));
        startWorks.add(new CreateCharacter(CHARACTER_VARIABLE));
        startWorks.add(new CreateDouble(DOUBLE_VARIABLE));
        startWorks.add(new CreateFloat(FLOAT_VARIABLE));
        startWorks.add(new CreateInteger(INT_VARIABLE));
        startWorks.add(new CreateLong(LONG_VARIABLE));
        startWorks.add(new CreateString(STRING_VARIABLE));
        // SET
        startWorks.add(new CreateBooleanSet(SET_BOOLEAN));
        startWorks.add(new CreateCharacterSet(SET_CHARACTER));
        startWorks.add(new CreateDoubleSet(SET_DOUBLE));
        startWorks.add(new CreateFloatSet(SET_FLOAT));
        startWorks.add(new CreateIntegerSet(SET_INTEGER));
        startWorks.add(new CreateLongSet(SET_LONG));
        startWorks.add(new CreateStringSet(SET_STRING));
        startWorks.add(new SetAdd(SET_ADD));
        // LIST
        startWorks.add(new CreateBooleanList(LIST_BOOLEAN));
        startWorks.add(new CreateCharacterList(LIST_CHARACTER));
        startWorks.add(new CreateDoubleList(LIST_DOUBLE));
        startWorks.add(new CreateFloatList(LIST_FLOAT));
        startWorks.add(new CreateIntegerList(LIST_INTEGER));
        startWorks.add(new CreateLongList(LIST_LONG));
        startWorks.add(new CreateStringList(LIST_STRING));
        startWorks.add(new ListAdd(LIST_ADD));
        // MAP
        startWorks.add(new CreateBooleanMap(MAP_BOOLEAN));
        startWorks.add(new CreateCharacterMap(MAP_CHARACTER));
        startWorks.add(new CreateDoubleMap(MAP_DOUBLE));
        startWorks.add(new CreateFloatMap(MAP_FLOAT));
        startWorks.add(new CreateIntegerMap(MAP_INTEGER));
        startWorks.add(new CreateLongMap(MAP_LONG));
        startWorks.add(new CreateStringMap(MAP_STRING));

        startWorks.add(new PrintSpace(PRINT_SPACE));
        startWorks.add(new PrintTap(PRINT_TAP));
        startWorks.add(new Println(PRINTLN));
        startWorks.add(new Print(PRINT));
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

        // POISON
        startWorks.add(new Poison(POISON));
    }

    private static void reset() {
//        total.setLength(0);
//        LOOP_TOKEN.clear();
        returnWorks.clear();
        startWorks.clear();
        repository.clear();
        priorityWorks.clear();
        noUse.clear();
        COPY_REPOSITORY.clear();
    }
}
