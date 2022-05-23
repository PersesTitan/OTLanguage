package loop;

import item.Check;
import item.Setting;

import java.util.regex.Pattern;

public class For extends Setting implements Check {
    private static final String SPECIFIED = "^^";
    private final String patternText = "\\n\\s*\\^\\^\\s|^\\s*\\^\\^\\s";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
