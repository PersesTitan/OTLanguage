package Variable;

import item.Check;
import item.KeyValueItem;
import item.Setting;
import item.VariableWork;

import java.util.regex.Pattern;

public class DoubleP extends Setting implements Check, VariableWork {
    private static final String SPECIFIED = "ㅇㅆㅇ";
    private final String patternText = "\\n\\s*ㅇㅆㅇ\\s|^\\s*ㅇㄱㅇ\\s";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) throws Exception {
        KeyValueItem keyValue = setKeyValue(SPECIFIED, line);
        String key = keyValue.getKey();
        String value = keyValue.getValue();
        DM.put(key, Double.valueOf(value));
        set.add(key);
    }
}
