package item;

import Variable.Variable;
import print.ScannerP;
import Variable.*;

import javax.swing.text.StyledEditorKit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Setting {
    protected ScannerP scannerP = new ScannerP();
    protected Variable variable = new Variable();

    protected BooleanP booleanP = new BooleanP();
    protected CharacterP characterP = new CharacterP();
    protected DoubleP doubleP = new DoubleP();
    protected FloatP floatP = new FloatP();
    protected IntegerP integerP = new IntegerP();
    protected LongP longP = new LongP();
    protected StringP stringP = new StringP();

    public Set<String> set = new HashSet<>();
    public Map<String, Integer> IM = new HashMap<>();
    public Map<String, Long> LM = new HashMap<>();
    public Map<String, Boolean> BM = new HashMap<>();
    public Map<String, String> SM = new HashMap<>();
    public Map<String, Character> CM = new HashMap<>();
    public Map<String, Float> FM = new HashMap<>();
    public Map<String, Double> DM = new HashMap<>();

    public KeyValueItem setKeyValue(String SPECIFIED, String line) throws Exception {
        int start = line.indexOf(SPECIFIED) + SPECIFIED.length();
        int end = line.indexOf(":");
        String key = line.substring(start, end).strip();
        if (set.contains(key)) throw new Exception("이미 존재하는 변수 이름 입니다.");
        String value = line.substring(end+1);
        value = scannerP.start(value);
        return new KeyValueItem(key, value);
    }

    /**
     * @param word key 값을 받아옴
     * @return 변수에 저장된 값을 반환함
     */
    protected String checkValue(String word) {
        if (BM.containsKey(word)) {
            if (BM.get(word)) return "ㅇㅇ";
            else return "ㄴㄴ";
        } else if (CM.containsKey(word)) return CM.get(word).toString();
        else if (DM.containsKey(word)) return DM.get(word).toString();
        else if (FM.containsKey(word)) return FM.get(word).toString();
        else if (IM.containsKey(word)) return IM.get(word).toString();
        else if (LM.containsKey(word)) return LM.get(word).toString();
        else return SM.getOrDefault(word, "");
    }
}