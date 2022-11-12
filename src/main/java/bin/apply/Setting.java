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
import bin.orign.console.normal.*;
import bin.orign.console.normal.Scanner;
import bin.orign.console.priority.PriorityPrint;
import bin.orign.console.priority.PriorityPrintSpace;
import bin.orign.console.priority.PriorityPrintTap;
import bin.orign.console.priority.PriorityPrintln;
import bin.orign.loop.For;
import bin.orign.loop.ForEach;
import bin.orign.loop.While;
import bin.orign.loop.If;
import bin.CreateReturnWorks;
import bin.CreateStartWorks;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.io.*;
import java.util.*;

import static bin.apply.sys.item.Separator.*;
import static bin.token.ConsoleToken.*;
import static bin.token.LoopToken.*;
import static bin.token.cal.BoolToken.*;
import static bin.token.cal.NumberToken.*;

public class Setting implements Repository {
    public static final HashMap<String, Map<String, Object>> COPY_REPOSITORY = new HashMap<>();
    public static final StringBuilder total = new StringBuilder();
    // 기본 모드 = INFO
    public static DebugMode debugMode = DebugMode.INFO;
    public static RunType runType;

    public static String mainPath;
    public static String path;

    public static void start(String line, String errorLine,
                             LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (line.isBlank() || line.startsWith(REMARK)) return;

        if (CreateStartWorks.start(line, true, repositoryArray)) return;
        line = lineStart(line, repositoryArray);
        for (var work : startWorks) {if (work.check(line)) {work.start(line, errorLine, repositoryArray);return;}}

        if (CreateStartWorks.start(line, false, repositoryArray)) return;

        runMessage(errorLine);
    }

    // ============================================================================== //
    public static String lineStart(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (line.contains(VARIABLE_GET_S) && line.contains(VARIABLE_GET_E)) {
            for (var works : returnWorks) if (works.check(line)) line = works.start(line, repositoryArray);
            line = CreateReturnWorks.start(line, repositoryArray);
        }
        line = Controller.boolCalculator.start(line);
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

    // 모듈 반환
    private static HashMap<String, Map<String, StartWorkV3>> priority() {
        HashMap<String, Map<String, StartWorkV3>> total = new HashMap<>();
        for (File files : Objects.requireNonNull(new File(MODULE_PATH + SEPARATOR_FILE + COMPULSION).listFiles())) {
            if (!files.getName().toLowerCase(Locale.ROOT).endsWith(".otlm")) continue;
            HashMap<String, Map<String, StartWorkV3>> v = readStart(files);
            if (v != null) total.putAll(v);
        }
        return total;
    }

    private static HashMap<String, Map<String, StartWorkV3>> start() {
        HashMap<String, Map<String, StartWorkV3>> total = new HashMap<>();
        for (File files : Objects.requireNonNull(new File(MODULE_PATH + SEPARATOR_FILE + OPERATE).listFiles())) {
            if (!files.getName().toLowerCase(Locale.ROOT).endsWith(".otlm")) continue;
            var v = readStart(files);
            if (v != null) total.putAll(v);
        }
        return total;
    }

    static {
        noUse.add(SCANNER);
        noUse.add(TRUE);
        noUse.add(FALSE);
        noUse.add(NOT);
        noUse.add(OR);
        noUse.add(AND);
        noUse.add(MINUS_CALCULATOR_S);
        noUse.addAll(MAP_LIST);

        repository.add(new HashMap<>());
        repository.get(0).put(METHOD, new HashMap<>());

        TOTAL_LIST.forEach(v -> COPY_REPOSITORY.put(v, new HpMap(v)));
        TOTAL_LIST.forEach(v -> repository.getFirst().put(v, new HpMap(v)));

        returnWorks.add(new MethodReturn());

        startWorks.add(new If(IF, ELSE_IF, ELSE));
        startWorks.add(new For());
        startWorks.add(new While(WHITE));
        startWorks.add(new ForEach());
        startWorks.add(new DefineMethod(METHOD));
        startWorks.add(new MethodVoid());

        try {
            for (File files : Objects.requireNonNull(new File(MODULE_PATH + SEPARATOR_FILE + COMPULSION).listFiles())) {
                if (!files.getName().toLowerCase(Locale.ROOT).endsWith(".otlm")) continue;
                HashMap<String, Map<String, StartWorkV3>> v = readStart(files);
                if (v != null) priorityStartWorksV3.putAll(v);
            }

            for (File files : Objects.requireNonNull(new File(MODULE_PATH + SEPARATOR_FILE + OPERATE).listFiles())) {
                if (!files.getName().toLowerCase(Locale.ROOT).endsWith(".otlm")) continue;
                var v = readStart(files);
                if (v != null) startWorksV3.putAll(v);
            }

            for (File files : Objects.requireNonNull(new File(MODULE_PATH + SEPARATOR_FILE + ALTERATION).listFiles())) {
                if (!files.getName().toLowerCase(Locale.ROOT).endsWith(".otlm")) continue;
                try (ObjectInput input = new ObjectInputStream(new FileInputStream(files))) {
                    returnWorksV3.putAll(((HashMap<String, Map<String, ReturnWorkV3>>) input.readObject()));
                } catch (IOException | ClassNotFoundException e) {
                    Setting.warringMessage(String.format("%s를 모듈에 추가하지 못하였습니다.", files.getName()));
                }
            }
        } catch (NullPointerException ignored) {
            Setting.errorMessage("모듈 추가에 실패하였습니다.");
        }

//        CreateOrigin createOrigin = new CreateOrigin();
//        CreateSet createSet = new CreateSet();
//        CreateList createList = new CreateList();
//        CreateMap createMap = new CreateMap();
//        ORIGIN_LIST.forEach(v -> Repository.createStartWorks(v, "", createOrigin));
//        SET_LIST.forEach(v -> Repository.createStartWorks(v, "", createSet));
//        LIST_LIST.forEach(v -> Repository.createStartWorks(v, "", createList));
//        MAP_LIST.forEach(v -> Repository.createStartWorks(v, "", createMap));

//        Repository.priorityCreateStartWorks(FORCE_QUIT, "", new ForceQuit(0));
//        Repository.priorityCreateStartWorks(PRIORITY_PRINT, "", new PriorityPrint(1));
//        Repository.priorityCreateStartWorks(PRIORITY_PRINTLN, "", new PriorityPrintln(1));
//        Repository.priorityCreateStartWorks(PRIORITY_PRINT_TAP, "", new PriorityPrintTap(1));
//        Repository.priorityCreateStartWorks(PRIORITY_PRINT_SPACE, "", new PriorityPrintSpace(1));
//        Repository.priorityCreateStartWorks(TRY_CATCH, "", new TryCatch(1));

//        Repository.createReturnWorks(RANDOM_BOOL, "", new RandomBoolean(1));
//        Repository.createReturnWorks(RANDOM_DOUBLE, "", new RandomDouble(1, 2));
//        Repository.createReturnWorks(RANDOM_FLOAT, "", new RandomFloat(1, 2));
//        Repository.createReturnWorks(RANDOM_INTEGER, "", new RandomInteger(1, 2));
//        Repository.createReturnWorks(RANDOM_LONG, "", new RandomLong(1, 2));
//        Repository.createReturnWorks(SCANNER, "", new Scanner(0));

//        Repository.createReturnWorks(STRING_VARIABLE, JOIN, new Join(2));
//        Repository.createReturnWorks(STRING_VARIABLE, SPLIT, new Split(2));
//        Repository.createReturnWorks(STRING_VARIABLE, SPLIT_REGULAR, new SplitRegular(2));
//        Repository.createReturnWorks(STRING_VARIABLE, CONTAINS_S, new Contains(2));
//        Repository.createReturnWorks(STRING_VARIABLE, EQUALS_S, new Equals(2));

//        Repository.createStartWorks(PRINT, "", new Print(1));
//        Repository.createStartWorks(PRINTLN, "", new Println(1));
//        Repository.createStartWorks(PRINT_SPACE, "", new PrintSpace(1));
//        Repository.createStartWorks(PRINT_TAP, "", new PrintTap(1));
//        Repository.createStartWorks(SLEEP, "", new Sleep(1));
//        Repository.createStartWorks(FILE, "", new FilePath(1));

//        Repository.createStartWorks(POISON, "", new PoisonCreate(1, 2));
//        Repository.createStartWorks(POISON, POISON_START, new PoisonStart(0));
//        Repository.createStartWorks(POISON, POISON_POST, new PoisonMethod(HttpMethod.POST));
//        Repository.createStartWorks(POISON, POISON_GET, new PoisonMethod(HttpMethod.GET));
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
