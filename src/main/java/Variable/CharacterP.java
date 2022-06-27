package Variable;

import item.KeyValueItem;
import item.Setting;
import item.kind.VarType;
import item.work.VariableWork;

import java.util.regex.Pattern;

public class CharacterP extends Setting implements VariableWork {
    public static final String SPECIFIED = "ㅇㄱㅇ";
    public static final String patternText = "(\\n|^)\\s*ㅇㄱㅇ\\s";
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
        //char 형인지 확인
        if (!varCheck.check(value, VarType.Character)) throw new Exception(typeErrorMessage);
        CM.put(key, value.charAt(0));
        set.add(key);
    }
}
