package bin.apply;

import bin.apply.sys.item.Color;
import bin.apply.sys.item.DebugMode;
import bin.apply.sys.item.RunType;
import bin.CreateReturnWorks;
import bin.CreateStartWorks;
import bin.orign.loop.For;
import org.fusesource.jansi.Ansi;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.io.*;
import java.util.*;

import static bin.apply.sys.item.Separator.*;
import static bin.token.LoopToken.*;
import static org.fusesource.jansi.Ansi.ansi;

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
        if (debugMode.ordinal() < DebugMode.WARRING.ordinal())
            System.out.printf("%s%s%s\n", Color.YELLOW, message, Color.RESET);
    }

    public static void errorMessage(String message) {
        if (debugMode.ordinal() < DebugMode.ERROR.ordinal())
            System.out.printf("%s%s%s\n", Color.RED, message, Color.RESET);
    }

    static {
        try {
            for (File files : Objects.requireNonNull(new File(MODULE_PATH + SEPARATOR_FILE + COMPULSION).listFiles())) {
                if (!files.getName().toLowerCase(Locale.ROOT).endsWith(".otlm")) continue;
                readStart(files, priorityStartWorksV3);
            }

            for (File files : Objects.requireNonNull(new File(MODULE_PATH + SEPARATOR_FILE + OPERATE).listFiles())) {
                if (!files.getName().toLowerCase(Locale.ROOT).endsWith(".otlm")) continue;
                readStart(files, startWorksV3);
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
    }

    private static void readStart(File file, HashMap<String, Map<String, StartWorkV3>> map) {
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(file))) {
            map.putAll((HashMap<String, Map<String, StartWorkV3>>) input.readObject());
        } catch (IOException | ClassNotFoundException e) {
            Setting.warringMessage(String.format("%s를 모듈에 추가하지 못하였습니다.", file.getName()));
        }
    }
}
