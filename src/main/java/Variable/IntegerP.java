package Variable;

import item.Check;
import item.Setting;
import item.VariableWork;

public class IntegerP extends Setting implements Check, VariableWork {

    private static final String SPECIFIED = "ㅇㅈㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }

    @Override
    public void start(String line) {
        int start = line.indexOf(SPECIFIED) + SPECIFIED.length();
        int end = line.indexOf(":");
        String key = line.substring(start, end).strip();
        String value = line.substring(end+1);
        value = scannerP.start(value);
        IM.put(key, Integer.valueOf(value));
    }
}
