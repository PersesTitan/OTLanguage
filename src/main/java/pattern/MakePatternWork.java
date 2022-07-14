package pattern;

import java.util.regex.Pattern;

public interface MakePatternWork {
    default boolean check(String line, Pattern pattern) {
        return pattern.matcher(line).find();
    }
    void start(String line);
}