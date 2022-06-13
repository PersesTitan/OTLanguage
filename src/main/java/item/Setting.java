package item;

import item.kind.LoopType;
import item.kind.VarType;
import loop.For;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Setting implements ActivityItem {
    public final static Map<String, String> uuidMap = new HashMap<>();
    public final StringBuffer total = new StringBuffer();
    public final Map<Integer, String> idLine = new HashMap<>();
    /*===========================================*/
    public final Set<String> set = new HashSet<>();
    public final Map<String, Integer> IM = new HashMap<>();
    public final Map<String, Long> LM = new HashMap<>();
    public final Map<String, Boolean> BM = new HashMap<>();
    public final Map<String, String> SM = new HashMap<>();
    public final Map<String, Character> CM = new HashMap<>();
    public final Map<String, Float> FM = new HashMap<>();
    public final Map<String, Double> DM = new HashMap<>();
    /*===========================================*/
    public final Map<String, String> IFM = new HashMap<>();
    public final Map<String, String> FORM = new HashMap<>();

    /**
     * 변수 감시시에 변수 변경 <br>
     * scanner 감지하였을때 입력 받음 <br>
     * @param line 라인 받아오기
     */
    protected void start(String line) throws Exception {
        if (!line.isBlank()) {
            if (tryCatch.check(line)) tryCatch.start(line);
            else if (tryCatchPrint.check(line)) tryCatchPrint.start(line);
            else {
                if (variable.check(line)) line = variable.getVar(line);
                if (scannerP.check(line)) line = scannerP.start(line);

                if (print.check(line)) print.start(line);
                else if (println.check(line)) println.start(line);
                else if (booleanP.check(line)) booleanP.start(line.strip());
                else if (characterP.check(line)) characterP.start(line.strip());
                else if (doubleP.check(line)) doubleP.start(line.strip());
                else if (floatP.check(line)) floatP.start(line.strip());
                else if (integerP.check(line)) integerP.start(line.strip());
                else if (longP.check(line)) longP.start(line.strip());
                else if (stringP.check(line)) stringP.start(line.strip());
            }
        }
    }

    /**
     * @param text 텍스트 확인하는 라인
     * @return 존재시 존재하는 타입 반환
     */
    public LoopType loopCheck (String text) {
        if (ifP.check(text)) return LoopType.If;
        else if (forP.check(text)) return LoopType.For;
        else return LoopType.Etc;
    }

    /**
     * 변수 초기화시키는 작업
     */
    public void varClear() {
        total.setLength(0);
        idLine.clear();

        set.clear();
        IM.clear();
        LM.clear();
        BM.clear();
        SM.clear();
        CM.clear();
        FM.clear();
        DM.clear();

        IFM.clear();
        FORM.clear();
    }

    /**
     * @param SPECIFIED 타입 받아오기
     * @param line 한 줄값 받아오기
     * @return key, value 값을 반환함
     * @throws Exception 변수가 이미 존재하면 에러 반환함
     */
    public KeyValueItem setKeyValue(String SPECIFIED, String line) throws Exception {
        int start = line.indexOf(SPECIFIED) + SPECIFIED.length();
        if (line.substring(start).isBlank()) throw new Exception("초기값이 존재 하지 않습니다.");
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

    /**
     * @param line boolean 식을 받은 뒤 값을 계산하는 식입니다.
     * @return bool 계산 후 반환하는 값
     */
    public boolean changeBool(String line) throws Exception {
        if (line.equals("true") || line.equals("false")) return Boolean.parseBoolean(line);
        else {
            String[] sign = line.split("false|true");
            String[] bools = line.split("[ㄲㄸ]");
            assert sign.length+1 == bools.length;
            boolean bool = Boolean.parseBoolean(bools[0]);
            for (int i = 0; i<sign.length; i++) {
                if (varCheck.check(sign[i], VarType.Boolean)) throw new Exception(typeErrorMessage);
                if (sign[i].equals("ㄲ")) bool = bool && Boolean.parseBoolean(sign[i+1]);
                else bool = bool || Boolean.parseBoolean(sign[i+1]);
            } return bool;
        }
    }
}