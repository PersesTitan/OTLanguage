package pattern;

import java.util.regex.Pattern;

public interface MakePatternWork {
    boolean check(String line);
    Pattern getPattern();
    void start(String line);
}