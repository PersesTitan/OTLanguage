package bin.token;

import bin.apply.mode.DebugMode;

import java.util.Locale;
import java.util.Objects;

public interface SepToken {
    boolean isWindow = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win");

    String FILE = System.getProperty("file.separator");              // /
    String HOME = System.getProperty("user.home");                   // /User/name
    String LINE = System.lineSeparator();                            // \n \r

    String MODULE = "module";

    String INSTALL_PATH = DebugMode.isDevelopment()
            ? getPath(HOME, ".otl")
            : System.getenv().getOrDefault("OTL_HOME", getPath(HOME, ".otl"));

    // [a, b, c] => a/b/c
    static String getPath(String...line) {
        return String.join(FILE, line);
    }

    static String getResource(String name) {
        return Objects.requireNonNull(SepToken.class.getResource(name)).getPath();
    }
}
