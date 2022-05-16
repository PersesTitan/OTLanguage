package Variable;

import item.Check;
import item.KeyValue;
import item.Setting;
import item.VariableWork;

public class LongP extends Setting implements Check, VariableWork {

    private static final String SPECIFIED = "ㅇㅉㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }

    @Override
    public void start(String line) throws Exception {
        KeyValue keyValue = setKeyValue(SPECIFIED, line);
        String key = keyValue.getKey();
        String value = keyValue.getValue();
        LM.put(key, Long.valueOf(value));
        set.add(key);
    }
}
