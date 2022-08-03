package make.method.define;

import java.util.regex.Pattern;

public interface MakePatternVoid {
    boolean check(String line);
    String getPatternText();
    Pattern getPattern();
}
