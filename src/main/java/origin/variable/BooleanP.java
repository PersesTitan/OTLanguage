package origin.variable;

import origin.item.KeyValueItem;
import origin.item.Setting;
import origin.item.work.VariableWork;

import java.util.regex.Pattern;

public class BooleanP extends Setting implements VariableWork {
    public static final String SPECIFIED = "ㅇㅂㅇ";
    public static final String patternText = "(\\n|^)\\s*ㅇㅂㅇ\\s";
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
        value = value.replace("ㅇㅇ", "true");
        value = value.replace("ㄴㄴ", "false");
        value = value.replace(" ", "");
        BM.put(key, changeBool(value));
        set.add(key);
    }
}