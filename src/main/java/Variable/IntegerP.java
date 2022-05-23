package Variable;

import item.Check;
import item.KeyValueItem;
import item.Setting;
import item.work.VariableWork;

import java.util.regex.Pattern;

public class IntegerP extends Setting implements Check, VariableWork {
    private static final String SPECIFIED = "ㅇㅈㅇ";
    private final String patternText = "\\n\\s*ㅇㅈㅇ\\s|^\\s*ㅇㅈㅇ\\s";
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
        IM.put(key, Integer.valueOf(value));
        set.add(key);
    }
}
