package origin.variable;

import origin.item.KeyValueItem;
import origin.item.Setting;
import origin.item.kind.VarType;
import origin.item.work.VariableWork;

import java.util.regex.Pattern;

public class IntegerP extends Setting implements VariableWork {
    public static final String SPECIFIED = "ㅇㅈㅇ";
    public static final String patternText = "(\\n|^)\\s*ㅇㅈㅇ\\s";
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
        if (!varCheck.check(value, VarType.Integer)) throw new Exception(typeErrorMessage);
        IM.put(key, Integer.valueOf(value));
        set.add(key);
    }
}
