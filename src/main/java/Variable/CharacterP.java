package Variable;

import item.Check;
import item.KeyValueItem;
import item.Setting;
import item.VariableWork;

public class CharacterP extends Setting implements Check, VariableWork {

    private static final String SPECIFIED = "ㅇㄱㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
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
