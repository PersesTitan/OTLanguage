package Variable;

import item.Check;
import item.KeyValueItem;
import item.Setting;
import item.kind.VarType;
import item.work.VariableWork;

import java.util.regex.Pattern;

public class DoubleP extends Setting implements Check, VariableWork {
    public static final String SPECIFIED = "ㅇㅆㅇ";
    public static final String patternText = "(\\n|^)\\s*ㅇㅆㅇ\\s";
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
        if (!varCheck.check(value, VarType.Double)) throw new Exception(typeErrorMessage);
        DM.put(key, Double.valueOf(value));
        set.add(key);
    }
}
