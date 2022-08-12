package make.custom;

import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.define.VariableCheck;

import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface CustomClassWork {
    boolean check(String line);
    void start(String line);

    //variableKind : ㅇㅁㅇ, ㅇㄹㅇ, ㅇㅅㅇ, ...
    //variableType : 변수명
    //value : 변수에 넣을 값
    static void setValue(String variableKind, String name, String value,
                         Map<String, Map<String, Object>> repository,
                         Set<String> set) {
        if (set.contains(name)) //이미 존재하는 변수명인지 확인
            throw new VariableException(variableKind + VariableMessage.doNotFind);
        if (!repository.containsKey(variableKind)) //정의된 변수 타입인지 확인
            throw new VariableException(variableKind + VariableMessage.doNotDefine);
        if (!VariableCheck.check(value, variableKind)) //타입 확인
            throw new VariableException(VariableMessage.typeMatchError);
        //변수명, 값 이 저장되있는 맵
        repository.get(variableKind).put(name, value);
        set.add(name);
    }

    //변수값을 넣는 메소드
    static void putValues(String group, String str, int count,
                          Map<String, Map<String, Object>> repository,
                          Set<String> set) {
        String[] values = group.substring(1, group.length()-1).split("]\\["); //변수에 넣을 값
        String[] vs = str.substring(1, str.length()-1).split("]\\["); //ㅇㅅㅇ 변수명, ㅇㅁㅇ 변수명
        if (values.length != vs.length) throw new MatchException(MatchMessage.grammarError);
        if (vs.length != count) throw new MatchException(MatchMessage.grammarError);
        for (int i = 0; i<count; i++) {
            StringTokenizer tokenizer = new StringTokenizer(vs[i].trim());
            String value = values[i].trim();
            String variableType = tokenizer.nextToken(); //ㅇㅅㅇ, ㅇㅁㅇ, ㅇㅂㅇ, ...
            String variableName = tokenizer.nextToken(); //변수명1, 변수명2, ...
            if (variableType == null) throw new MatchException(MatchMessage.grammarError);
            if (variableName == null) throw new MatchException(MatchMessage.grammarError);
            //값을 입력하는 작업
            CustomClassWork.setValue(variableType, variableName, value, repository, set);
        }
    }

    //클래스 변수, 전체 변수, 메소드 변수를 구분하여 값을 반환함
    //repository : 메소드 저장소, 클래스 저장소, 전체 저장소
    @SafeVarargs
    static String getVariable(String line, Map<String, Map<String, Object>>...repository) {
        StringBuilder builder = new StringBuilder("~{0,");
        builder.append(repository.length-1).append("}");

        String patternText = ":"+builder+"[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+[ _]";
        Pattern pattern = Pattern.compile(patternText);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            String values = group.substring(1, group.length()-1); //~~변수명
            // 변수명
            String value = values.replaceFirst("^"+builder, "");
            Matcher m = Pattern.compile("^"+builder).matcher(values);
            //m.group().length() : ~~ => 2
            if (m.find()) line = subGetVariable(line, repository[m.group().length()], value, patternText);
            else line = subGetVariable(line, repository[0], value, patternText);
        }
        return line;
    }

    //변수를 넣는 메소드
    //line : 코드 라인, repository : 저장소 , value 변수 이름, patternText 수정되는 패던 라인
    static String subGetVariable(String line,
                                 Map<String, Map<String, Object>> repository,
                                 String value, String patternText) {
        for (String key : repository.keySet()) {
            if (repository.get(key).containsKey(value))
                line = line.replaceFirst(patternText, repository.get(key).get(value).toString());
        }
        return line;
    }

    //variable : 변수명 - ~~변수
    //return -> 갯수 반환
    static int getRepository(String variable) {
        if (!variable.startsWith("~")) return 0;
        Pattern pattern = Pattern.compile("^~+");
        Matcher matcher = pattern.matcher(variable);
        if (matcher.find()) return matcher.group().length();
        return 0;
    }

    //변수값 업데이트 로직 체크
    static boolean checkVariableUpdate(String line) {
        String patternText = "^\\s*~*[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+:";
        Pattern pattern = Pattern.compile(patternText);
        return pattern.matcher(line).find();
    }
}
