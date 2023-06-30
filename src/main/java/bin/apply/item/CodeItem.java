package bin.apply.item;

import bin.apply.line.LineTool;
import bin.apply.work.system.Import;
import bin.exception.Error;
import bin.exception.FileException;
import bin.exception.SystemException;
import bin.token.SepToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class CodeItem implements Serializable {
    @Serial
    private static final long serialVersionUID = -3491013047653565560L;
    public static final String VERSION = "5.0.0";
    public static CodeItem MAIN = null;
    public static String RUN_PATH = null;

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

    public void start(int s, final int e) {
        try {
            while (s<e) s=start(s);
        } catch (Error error) {
            throw error.setMessage(PATH, FILES[s], s+1);
        }
    }

    public void startTry(int s, final int e) {
        while (s<e) s=start(s);
    }

    protected int start(int i) {
        return LINES[i] == null ? i+1 : LINES[i].start(i);
    }

    public String getShell() {
        return String.join(" ", IMPORT);
    }

    // static
    public static void addModule(String module) {
        if (MAIN != null) MAIN.IMPORT.add(module);
        else throw SystemException.CREATE_ERROR.getThrow(module);
    }

    public static boolean contains(String module) {
        if (MAIN != null) return MAIN.IMPORT.contains(module);
        else throw SystemException.CREATE_ERROR.getThrow(module);
    }

    // get fileName
    public static String getModulePath(String moduleName) {
        return getModulePath(moduleName, null);
    }

    public static String getModulePath(String moduleName, String file) {
        if (RUN_PATH == null) throw SystemException.FILE_ERROR.getThrow(moduleName);
        else if (file == null) return SepToken.getPath(RUN_PATH, SepToken.MODULE, moduleName);
        else return SepToken.getPath(RUN_PATH, SepToken.MODULE, moduleName, file);
    }

    @Serial
    private void readObject(ObjectInputStream ois) {
        try {
            ois.defaultReadObject();
            MAIN = this;
            for (String models : IMPORT) new Import(models);
        } catch (IOException | ClassNotFoundException e) {
            throw FileException.DO_NOT_INCLUDE.getThrow(e.getMessage());
        }
    }
}
