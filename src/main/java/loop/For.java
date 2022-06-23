package loop;

import item.Check;
import item.Setting;

import java.util.regex.Pattern;

public class For extends Setting implements Check {
    //[숫자]^[숫자]^[숫자]
    static final String patternText = "(\\n|^\\s)\\d\\^\\d\\^\\d(\\s|$)";
    private static final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
