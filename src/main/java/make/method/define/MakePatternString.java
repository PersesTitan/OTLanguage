package make.method.define;

import java.util.regex.Pattern;

public interface MakePatternString {
    boolean check(String line);
    String getPatternText();
    Pattern getPattern();
}
