package Variable;

import item.Check;
import item.KeyValueItem;
import item.Setting;
import item.VariableWork;

public class StringP extends Setting implements Check, VariableWork {

    private static final String SPECIFIED = "ㅇㅁㅇ";

    @Override
    public boolean check(String line) {
        return line.strip().startsWith(SPECIFIED);
    }

    /**
     * @param line 라인 값 받아 오기
     * @throws Exception 변수를 찾을 수 없을 시 에러 발생
     */
    @Override
    public void start(String line) throws Exception {
        KeyValueItem keyValue = setKeyValue(SPECIFIED, line);
        String key = keyValue.getKey();
        String value = keyValue.getValue();
        SM.put(key, value);
        set.add(key);
    }
}