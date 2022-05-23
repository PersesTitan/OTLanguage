package item;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Setting implements ActivityItem {
    /*===========================================*/
    //n번째 위치와 라인의 값을 저장하는 곳
    //변수 이름 저장하는 장소
    public static final Map<Integer, String> idLine = new HashMap<>();
    public static final Set<String> set = new HashSet<>();
    public static final Map<String, Integer> IM = new HashMap<>();
    public static final Map<String, Long> LM = new HashMap<>();
    public static final Map<String, Boolean> BM = new HashMap<>();
    public static final Map<String, String> SM = new HashMap<>();
    public static final Map<String, Character> CM = new HashMap<>();
    public static final Map<String, Float> FM = new HashMap<>();
    public static final Map<String, Double> DM = new HashMap<>();

    /**
     * 변수 초기화시키는 작업
     */
    public static void varClear() {
        idLine.clear();
        set.clear();
        IM.clear();
        LM.clear();
        BM.clear();
        SM.clear();
        CM.clear();
        FM.clear();
        DM.clear();
    }

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
    protected String checkValue(@NotNull String word) {
        if (BM.containsKey(word)) return BM.get(word) ? "ㅇㅇ" : "ㄴㄴ";
        else if (CM.containsKey(word)) return CM.get(word).toString();
        else if (DM.containsKey(word)) return DM.get(word).toString();
        else if (FM.containsKey(word)) return FM.get(word).toString();
        else if (IM.containsKey(word)) return IM.get(word).toString();
        else if (LM.containsKey(word)) return LM.get(word).toString();
        else return SM.getOrDefault(word, "");
    }
}