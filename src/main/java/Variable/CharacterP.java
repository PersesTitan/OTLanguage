package Variable;

import item.Check;
import item.KeyValueItem;
import item.Setting;
import item.work.VariableWork;

import java.util.regex.Pattern;

public class CharacterP extends Setting implements Check, VariableWork {
    private static final String SPECIFIED = "ㅇㄱㅇ";
    private final String patternText = "\\n\\s*ㅇㄱㅇ\\s|^\\s*ㅇㄱㅇ\\s";
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
        CM.put(key, value.charAt(0));
        set.add(key);
    }
}
