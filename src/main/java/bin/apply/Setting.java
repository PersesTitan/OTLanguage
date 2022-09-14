package bin.apply;

import bin.apply.sys.item.Color;
import bin.apply.sys.run.ForceQuit;
import bin.apply.sys.run.Sleep;
import bin.math.random.*;
import bin.orign.console.*;
import bin.orign.variable.list.create.*;
import bin.orign.variable.map.create.CreateBooleanMap;
import bin.orign.variable.origin.create.*;
import bin.orign.variable.set.create.*;
import bin.orign.loop.If;
import bin.orign.variable.SetVariable;
import bin.orign.variable.list.get.ListAdd;
import bin.orign.variable.list.get.ListClear;
import bin.orign.variable.list.get.ListIsEmpty;
import bin.orign.variable.list.get.ListSort;
import bin.orign.variable.origin.put.PutVariable;
import bin.orign.variable.set.get.*;

import java.util.Map;

import static bin.token.ConsoleToken.*;
import static bin.token.LoopToken.*;
import static bin.token.cal.NumberToken.*;

public class Setting implements Repository {
    public static final StringBuilder total = new StringBuilder();
    public static String path;

    @SafeVarargs
    public static void start(String line, String errorLine,
                             Map<String, Map<String, Object>>...repositoryArray) {
        if (line.isBlank()) return;
        String origen = line;
        for (var work : priorityWorks) {if (work.check(line)) {work.start(line, origen, repositoryArray); return;}}
        line = lineStart(line, repositoryArray);
        for (var work : startWorks) {if (work.check(line)) {work.start(line, origen, repositoryArray);return;}}

        System.out.printf("%s경고! %s는 실행되지 않은 라인 입니다.%s\n", Color.YELLOW, errorLine, Color.RESET);
    }

    public static String lineStart(String line, Map<String, Map<String, Object>>[] repositoryArray) {
        for (var work : returnWorks) {if (work.check(line)) line = work.start(line, repositoryArray);}
        line = Controller.boolCalculator.start(line);
        return line;
    }

    public static void firstStart() {
        reset();
        noUse.add(SCANNER);
        noUse.add(RANDOM_BOOL);
        noUse.add(RANDOM_DOUBLE);
        noUse.add(RANDOM_FLOAT);
        noUse.add(RANDOM_INTEGER);
        noUse.add(RANDOM_LONG);

        priorityWorks.add(new ForceQuit(FORCE_QUIT));
        priorityWorks.add(new CreateString(STRING_VARIABLE, repository));
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

        // ORIGEN
        startWorks.add(new CreateBoolean(BOOL_VARIABLE, repository));
        startWorks.add(new CreateCharacter(CHARACTER_VARIABLE, repository));
        startWorks.add(new CreateDouble(DOUBLE_VARIABLE, repository));
        startWorks.add(new CreateFloat(FLOAT_VARIABLE, repository));
        startWorks.add(new CreateInteger(INT_VARIABLE, repository));
        startWorks.add(new CreateLong(LONG_VARIABLE, repository));
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

        startWorks.add(new PrintSpace(PRINT_SPACE));
        startWorks.add(new PrintTap(PRINT_TAP));
        startWorks.add(new Println(PRINTLN));
        startWorks.add(new Print(PRINT));
        startWorks.add(new PutVariable());
        startWorks.add(new Sleep(SLEEP));
        startWorks.add(new If(IF, ELSE_IF, ELSE));
    }

    private static void reset() {
//        total.setLength(0);
//        LOOP_TOKEN.clear();
        returnWorks.clear();
        startWorks.clear();
        repository.clear();
        priorityWorks.clear();
        noUse.clear();
    }
}
