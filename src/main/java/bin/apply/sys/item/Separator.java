package bin.apply.sys.item;

public interface Separator {
    String SEPARATOR_FILE = System.getProperty("file.separator");              // /
    String SEPARATOR_PATH = System.getProperty("path.separator");              // :
    String SEPARATOR_LINE = System.getProperty("line.separator");              // \n \r
    String SEPARATOR_HOME = System.getProperty("user.home");                   // /User/name

    String MODULE_EXTENSION = ".otlm";
    String SYSTEM_EXTENSION = ".otls";
    String COMPULSION = "compulsion";   // 강제
    String ALTERATION = "alteration";   // 변경
    String OPERATE = "operate";         // 동작
    String MODULE = "module";
    String INSTALL_PATH = SEPARATOR_HOME + SEPARATOR_FILE + ".otl";        // /User/name/.otl
    String MODULE_PATH = INSTALL_PATH + SEPARATOR_FILE + MODULE;           // /User/name/.otl/module
    String COMPULSION_PATH = MODULE_PATH + SEPARATOR_FILE + COMPULSION;    // /User/name/.otl/module/compulsion
    String ALTERATION_PATH = MODULE_PATH + SEPARATOR_FILE + ALTERATION;    // /User/name/.otl/module/alteration
    String OPERATE_PATH = MODULE_PATH + SEPARATOR_FILE + OPERATE;          // /User/name/.otl/module/operate

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
