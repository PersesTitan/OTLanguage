package bin.apply.item;

import bin.apply.line.LineTool;
import bin.exception.Error;
import bin.exception.SystemException;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class CodeItem implements Serializable {
    @Serial
    private static final long serialVersionUID = -3491013047653565560L;
    public static final String VERSION = "5.0.0";
    public static CodeItem MAIN = null;
    public static String RUT_PATH = null;
//    public static final AtomicReference<CodeItem> MAIN = new AtomicReference<>();
//    public static final AtomicReference<String> RUN_PATH = new AtomicReference<>();

    private final List<String> IMPORT = new ArrayList<>();
    protected final String PATH;
    private final String[] FILES;
    private final LineTool[] LINES;

    public CodeItem(String PATH, String[] FILES, LineTool[] LINES) {
        this.PATH = PATH;
        this.FILES = FILES;
        this.LINES = LINES;
        MAIN = this;
    }

    public void start() {
        start(0, LINES.length);
    }

    public void start(int s, int e) {
        for (int i = s; i < e;) {
            try {
                i = start(i);
            } catch (Error error) {
                throw error.setMessage(PATH, FILES[i], i+1);
            }
        }
    }

    public void startTry(int s, int e) {
        for (int i = s; i < e;) i = start(i);
    }

    protected int start(int i) {
        return LINES[i] == null ? i+1 : LINES[i].start(i);
    }

    public String getShell() {
        return String.join(" ", IMPORT);
    }

    // static
    public static void addModule(String module) {
//        CodeItem item = MAIN.get();
        if (MAIN != null) MAIN.IMPORT.add(module);
        else throw SystemException.CREATE_ERROR.getThrow(module);
    }

    public static boolean contains(String module) {
//        CodeItem item = MAIN.get();
        if (MAIN != null) return MAIN.IMPORT.contains(module);
        else throw SystemException.CREATE_ERROR.getThrow(module);
    }
}
