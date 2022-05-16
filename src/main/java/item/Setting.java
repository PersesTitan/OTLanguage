package item;

import Variable.Variable;
import print.Print;
import print.Println;
import print.ScannerP;
import Variable.*;

import javax.swing.text.StyledEditorKit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Setting {
    protected final ScannerP scannerP = new ScannerP();
    protected final Print print = new Print();
    protected final Println println = new Println();

    protected final Variable variable = new Variable();
    protected final BooleanP booleanP = new BooleanP();
    protected final CharacterP characterP = new CharacterP();
    protected final DoubleP doubleP = new DoubleP();
    protected final FloatP floatP = new FloatP();
    protected final IntegerP integerP = new IntegerP();
    protected final LongP longP = new LongP();
    protected final StringP stringP = new StringP();

    /*===========================================*/
    //변수 이름 저장하는 장소
    //n번째 위치와 라인의 값을 저장하는 곳
    public final Set<String> set = new HashSet<>();
    public static final Map<Integer, String> idLine = new HashMap<>();
    public final Map<String, Integer> IM = new HashMap<>();
    public final Map<String, Long> LM = new HashMap<>();
    public final Map<String, Boolean> BM = new HashMap<>();
    public final Map<String, String> SM = new HashMap<>();
    public final Map<String, Character> CM = new HashMap<>();
    public final Map<String, Float> FM = new HashMap<>();
    public final Map<String, Double> DM = new HashMap<>();

    /**
     * @param SPECIFIED 타입 받아오기
     * @param line 한 줄값 받아오기
     * @return key, value 값을 반환함
     * @throws Exception 변수가 이미 존재하면 에러 반환함
     */
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
        if (BM.containsKey(word)) return BM.get(word) ? "ㅇㅇ" : "ㄴㄴ";
        else if (CM.containsKey(word)) return CM.get(word).toString();
        else if (DM.containsKey(word)) return DM.get(word).toString();
        else if (FM.containsKey(word)) return FM.get(word).toString();
        else if (IM.containsKey(word)) return IM.get(word).toString();
        else if (LM.containsKey(word)) return LM.get(word).toString();
        else return SM.getOrDefault(word, "");
    }
}