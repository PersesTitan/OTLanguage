package bin.apply;

import bin.apply.sys.item.Color;
import bin.apply.sys.item.HpMap;
import bin.apply.sys.item.RunType;
import bin.apply.sys.run.ForceQuit;
import bin.apply.sys.run.Sleep;
import bin.apply.sys.run.TryCatch;
import bin.define.item.MethodItem;
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
import cos.poison.Poison;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static bin.apply.Controller.variableDefault;
import static bin.token.ConsoleToken.*;
import static bin.token.LoopToken.*;
import static bin.token.cal.NumberToken.*;

public class Setting implements Repository {
    public static final HashMap<String, Map<String, Object>> COPY_REPOSITORY = new HashMap<>();
    public static final StringBuilder total = new StringBuilder();
    public static String mainPath;
    public static String path;
    public static RunType runType;

    @SafeVarargs
    public static void start(String line, String errorLine,
                             Map<String, Map<String, Object>>...repositoryArray) {
        if (line.isBlank()) return;
        String origen = line;
        for (var work : priorityWorks) {if (work.check(line)) {work.start(line, origen, repositoryArray); return;}}
        line = lineStart(line, repositoryArray);
        for (var work : startWorks) {if (work.check(line)) {work.start(line, origen, repositoryArray);return;}}

        runMessage(errorLine);
    }

    public static String lineStart(String line, Map<String, Map<String, Object>>[] repositoryArray) {
        if (variableDefault.check(line)) line = variableDefault.start(line);

        for (var work : returnWorks) {if (work.check(line)) line = work.start(line, repositoryArray);}
        line = Controller.boolCalculator.start(line);

        if (variableDefault.changeCheck(line)) line = variableDefault.changeStart(line);
        return line;
    }

    public static void runMessage(String errorLine) {
        System.out.printf("%s경고! %s는 실행되지 않은 라인 입니다.%s\n", Color.YELLOW, errorLine, Color.RESET);
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
        TOTAL_LIST.forEach(v -> COPY_REPOSITORY.put(v, new HpMap<>()));

        priorityWorks.add(new ForceQuit(FORCE_QUIT));
        priorityWorks.add(new PriorityPrint(PRIORITY_PRINT));
        priorityWorks.add(new PriorityPrintln(PRIORITY_PRINTLN));
        priorityWorks.add(new PriorityPrintTap(PRIORITY_PRINT_TAP));
        priorityWorks.add(new PriorityPrintSpace(PRIORITY_PRINT_SPACE));
        priorityWorks.add(new ListClear(LIST_CLEAR));
        priorityWorks.add(new ListSort(LIST_SORT));
        priorityWorks.add(new SetClear(SET_CLEAR));
        priorityWorks.add(new SetSort(SET_SORT));

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

        // ORIGEN
        startWorks.add(new CreateBoolean(BOOL_VARIABLE, repository));
        startWorks.add(new CreateCharacter(CHARACTER_VARIABLE, repository));
        startWorks.add(new CreateDouble(DOUBLE_VARIABLE, repository));
        startWorks.add(new CreateFloat(FLOAT_VARIABLE, repository));
        startWorks.add(new CreateInteger(INT_VARIABLE, repository));
        startWorks.add(new CreateLong(LONG_VARIABLE, repository));
        startWorks.add(new CreateString(STRING_VARIABLE, repository));
        // SET
        startWorks.add(new CreateBooleanSet(SET_BOOLEAN, repository));
        startWorks.add(new CreateCharacterSet(SET_CHARACTER, repository));
        startWorks.add(new CreateDoubleSet(SET_DOUBLE, repository));
        startWorks.add(new CreateFloatSet(SET_FLOAT, repository));
        startWorks.add(new CreateIntegerSet(SET_INTEGER, repository));
        startWorks.add(new CreateLongSet(SET_LONG, repository));
        startWorks.add(new CreateStringSet(SET_STRING, repository));
        startWorks.add(new SetAdd(SET_ADD));
        // LIST
        startWorks.add(new CreateBooleanList(LIST_BOOLEAN, repository));
        startWorks.add(new CreateCharacterList(LIST_CHARACTER, repository));
        startWorks.add(new CreateDoubleList(LIST_DOUBLE, repository));
        startWorks.add(new CreateFloatList(LIST_FLOAT, repository));
        startWorks.add(new CreateIntegerList(LIST_INTEGER, repository));
        startWorks.add(new CreateLongList(LIST_LONG, repository));
        startWorks.add(new CreateStringList(LIST_STRING, repository));
        startWorks.add(new ListAdd(LIST_ADD));
        // MAP
        startWorks.add(new CreateBooleanMap(MAP_BOOLEAN, repository));
        startWorks.add(new CreateCharacterMap(MAP_CHARACTER, repository));
        startWorks.add(new CreateDoubleMap(MAP_DOUBLE, repository));
        startWorks.add(new CreateFloatMap(MAP_FLOAT, repository));
        startWorks.add(new CreateIntegerMap(MAP_INTEGER, repository));
        startWorks.add(new CreateLongMap(MAP_LONG, repository));
        startWorks.add(new CreateStringMap(MAP_STRING, repository));

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
