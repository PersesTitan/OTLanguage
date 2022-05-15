package Variable;

import item.Check;
import item.Setting;
import item.VariableWork;
import print.ScannerP;

public class BooleanP extends Setting implements Check, VariableWork {

    private static final String SPECIFIED = "ㅇㅂㅇ";

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
        value = value.replace("ㅇㅇ", "true");
        value = value.replace("ㄴㄴ", "false");
        value = value.replace(" ", "");
        BM.put(key, change(value));
    }

    /**
     * @param line boolean 식을 받은 뒤 값을 계산하는 식입니다.
     * @return bool 계산 후 반환하는 값
     */
    private boolean change(String line) {
        if (line.equals("true") || line.equals("false")) return Boolean.parseBoolean(line);
        else {
            String[] sign = line.split("false|true");
            String[] bools = line.split("ㄲ|ㄸ");
            assert sign.length+1 == bools.length;
            boolean bool = Boolean.parseBoolean(bools[0]);
            for (int i = 0; i<sign.length; i++) {
                if (sign[i].equals("ㄲ")) bool = bool && Boolean.parseBoolean(sign[i+1]);
                else bool = bool || Boolean.parseBoolean(sign[i+1]);
            } return bool;
        }
    }
}