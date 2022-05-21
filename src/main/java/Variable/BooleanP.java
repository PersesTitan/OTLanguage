package Variable;

import item.Check;
import item.KeyValueItem;
import item.Setting;
import item.VariableWork;

public class BooleanP extends Setting implements Check, VariableWork {

    private static final String SPECIFIED = "ㅇㅂㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }

    @Override
    public void start(String line) throws Exception {
        KeyValueItem keyValue = setKeyValue(SPECIFIED, line);
        String key = keyValue.getKey();
        String value = keyValue.getValue();
        value = value.replace("ㅇㅇ", "true");
        value = value.replace("ㄴㄴ", "false");
        value = value.replace(" ", "");
        BM.put(key, change(value));
        set.add(key);
    }

    /**
     * @param line boolean 식을 받은 뒤 값을 계산하는 식입니다.
     * @return bool 계산 후 반환하는 값
     */
    private boolean change(String line) {
        if (line.equals("true") || line.equals("false")) return Boolean.parseBoolean(line);
        else {
            String[] sign = line.split("false|true");
            String[] bools = line.split("[ㄲㄸ]");
            assert sign.length+1 == bools.length;
            boolean bool = Boolean.parseBoolean(bools[0]);
            for (int i = 0; i<sign.length; i++) {
                if (sign[i].equals("ㄲ")) bool = bool && Boolean.parseBoolean(sign[i+1]);
                else bool = bool || Boolean.parseBoolean(sign[i+1]);
            } return bool;
        }
    }
}