package Variable;

import item.Check;
import item.KeyValueItem;
import item.Setting;
import item.VariableWork;

public class DoubleP extends Setting implements Check, VariableWork {

    private static final String SPECIFIED = "ㅇㅆㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
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
