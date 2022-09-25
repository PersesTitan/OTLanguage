package bin.apply.sys.item;

public interface Separator {
    String SEPARATOR_FILE = System.getProperty("file.separator");   // /
    String SEPARATOR_PATH = System.getProperty("path.separator");   // :
    String SEPARATOR_LINE = System.getProperty("line.separator");   // \n \r
    String SEPARATOR_HOME = System.getProperty("user.home");        // /User/name
}
