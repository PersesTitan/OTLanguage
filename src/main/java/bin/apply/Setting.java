package bin.apply;

import bin.apply.sys.item.Color;
import bin.apply.sys.item.DebugMode;
import bin.apply.sys.item.RunType;
import bin.CreateReturnWorks;
import bin.CreateStartWorks;
import bin.orign.loop.For;

import java.io.*;
import java.util.*;

import static bin.apply.sys.item.Separator.*;
import static bin.token.LoopToken.*;

public class Setting implements Repository {
    public static final StringBuilder total = new StringBuilder();
    // 기본 모드 = INFO
    public static DebugMode debugMode = DebugMode.INFO;
    public static RunType runType = RunType.Normal;

    public static String mainPath;
    public static String path;

    public static void start(String line, String errorLine,
                             LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (line.isBlank() || line.startsWith(REMARK)) return;

        if (CreateStartWorks.start(line, true, repositoryArray)) return;
        line = lineStart(line, repositoryArray);

        if (For.getInstance().check(line)) {For.getInstance().start(line, repositoryArray);return;}
        if (CreateStartWorks.start(line, false, repositoryArray)) return;

        runMessage(errorLine);
    }

    // ============================================================================== //
    public static String lineStart(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (line.contains(VARIABLE_GET_S) && line.contains(VARIABLE_GET_E))
            line = CreateReturnWorks.start(line, repositoryArray);
        return line;
    }

    // ============================================================================== //
    // 메세지 세팅
    public static void runMessage(String errorLine) {
        warringMessage(String.format("경고! %s는 실행되지 않은 라인 입니다.", errorLine));
    }

    public static void warringMessage(String message) {
        if (debugMode.check(DebugMode.WARRING))
            System.out.printf("%s%s%s\n", Color.YELLOW, message, Color.RESET);
    }

    public static void errorMessage(String message) {
        if (debugMode.check(DebugMode.ERROR))
            System.out.printf("%s%s%s\n", Color.RED, message, Color.RESET);
    }

    static {
        try {
            final String path =  MODULE_PATH + SEPARATOR_FILE;
            final File[] file1 = new File(path + COMPULSION).listFiles(Setting::checkModel);
            final File[] file2 = new File(path + OPERATE).listFiles(Setting::checkModel);
            final File[] file3 = new File(path + ALTERATION).listFiles(Setting::checkModel);
            if (file1 != null) for (File files : file1) readStart(files, priorityStartWorksV3);
            else Setting.errorMessage(path.concat(COMPULSION).concat(" 모듈 추가에 실패하였습니다."));
            if (file2 != null) for (File files : file2) readStart(files, startWorksV3);
            else Setting.errorMessage(path.concat(OPERATE).concat(" 모듈 추가에 실패하였습니다."));
            if (file3 != null) for (File files : file3) readStart(files, returnWorksV3);
            else Setting.errorMessage(path.concat(ALTERATION).concat(" 모듈 추가에 실패하였습니다."));
        } catch (NullPointerException ignored) {
            Setting.errorMessage("모듈 추가에 실패하였습니다.");
        }
    }

    private static boolean checkModel(File file) {
        return file.getName().toLowerCase(Locale.ROOT).endsWith(MODULE_EXTENSION);
    }

    private static <V> void readStart(File file, HashMap<String, Map<String, V>> map) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(file))) {
            map.putAll((HashMap<String, Map<String, V>>) input.readObject());
        } catch (IOException | ClassNotFoundException ignored) {
            Setting.warringMessage(String.format("%s를 모듈에 추가하지 못하였습니다.", file.getName()));
        }
    }
}
