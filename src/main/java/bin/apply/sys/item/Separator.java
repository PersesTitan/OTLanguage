package bin.apply.sys.item;

import bin.apply.sys.make.StartLine;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public interface Separator {
    boolean isWindow = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win");
    String SEPARATOR_FILE = System.getProperty("file.separator");              // /
    String SEPARATOR_PATH = System.getProperty("path.separator");              // :
    String SEPARATOR_LINE = System.getProperty("line.separator");              // \n \r
    String SEPARATOR_HOME = System.getProperty("user.home");                   // /User/name

    Map<String, String> EXT_REP = new HashMap<>();

    String MODULE_EXTENSION = ".otlm";
    String SYSTEM_EXTENSION = ".otls";
    String COMPULSION = "compulsion";   // 강제
    String ALTERATION = "alteration";   // 변경
    String OPERATE = "operate";         // 동작
    String MODULE = "module";

    // /User/name/.otl
    String INSTALL_PATH = StartLine.developmentMode
        ? getPath(SEPARATOR_HOME, ".otl")
        : System.getenv().getOrDefault("OTL_HOME", getPath(SEPARATOR_HOME, ".otl"));
    String MODULE_PATH = getPath(INSTALL_PATH, MODULE);           // /User/name/.otl/module
    String COMPULSION_PATH = getPath(MODULE_PATH, COMPULSION);    // /User/name/.otl/module/compulsion
    String ALTERATION_PATH = getPath(MODULE_PATH, ALTERATION);    // /User/name/.otl/module/alteration
    String OPERATE_PATH = getPath(MODULE_PATH, OPERATE);          // /User/name/.otl/module/operate
    // /User/name/.otl/module/system.otls
    String SYSTEM_PATH = getPath(MODULE_PATH, "system" + SYSTEM_EXTENSION);

//    System.getenv("OTL_HOME")
    static String getPath(String...line) {
        return String.join(SEPARATOR_FILE, line);
    }

    /*
        - /User/name/.otl/module - bin - system.otls
                                       - compulsion - a.otlm
                                       - alteration - b.otlm
                                       - operate    - c.otlm
                                 - cos - system.otls
                                       - compulsion - a.otlm
                                       - alteration - b.otlm
                                       - operate    - c.otlm
     */
}
