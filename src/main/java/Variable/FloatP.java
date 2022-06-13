package Variable;

import item.Check;
import item.KeyValueItem;
import item.Setting;
import item.kind.VarType;
import item.work.VariableWork;

import java.util.regex.Pattern;

public class FloatP extends Setting implements Check, VariableWork {
    private static final String SPECIFIED = "ㅇㅅㅇ";
    private final String patternText = "\\n\\s*ㅇㅅㅇ\\s|^\\s*ㅇㅅㅇ\\s";
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
        if (!varCheck.check(value, VarType.Float)) throw new Exception(typeErrorMessage);
        FM.put(key, Float.valueOf(value));
        set.add(key);
    }
}
