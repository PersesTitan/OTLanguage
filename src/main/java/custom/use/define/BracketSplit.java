package custom.use.define;

import custom.registration.define.RepositoryValue;
import event.Controller;
import event.Setting;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.variable.model.Repository;
import origin.variable.model.VariableWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static origin.variable.model.Repository.uuidMap;

public interface BracketSplit {
    //클래스 변수
    // line : [안녕하세요][1234]
    default void getVariables(String line, RepositoryValue repositoryValue,
                              Map<String, Map<String, Object>> repository,
                              Set<String> set) {
        if (line.isBlank()) return; //안녕하세요, 1234
        String[] vs = line.substring(1, line.length()-1).split("]\\[");
        List<String> varType = repositoryValue.varType(); // ㅇㅁㅇ, ㅇㅅㅇ
        List<String> methodName = repositoryValue.values(); //메소드명
        if (vs.length != varType.size()) throw new MatchException(MatchMessage.grammarError);
        for (int i = 0; i<varType.size(); i++) {
            String method = methodName.get(i).trim();
            repository.get(varType.get(i).trim()).put(method, vs[i]);
            set.add(method);
        }
    }

    //line : ㅋㅅㅋ 클래스명[ㅇㅁㅇ 변수명]
    //return : [ㅇㅁㅇ 변수명]
    //patternText : //[ㅇㅁㅇ 변수명] 추출
    default RepositoryValue getRepositoryValue(String line, String patternText) {
        List<String> varType = new ArrayList<>();
        List<String> values = new ArrayList<>();
        //[ㅇㅁㅇ 변수명]
        Matcher variables = Pattern.compile(patternText).matcher(line);
        if (variables.find()) {
            String vars = variables.group().strip(); //[ㅇㅁㅇ 변수명]
            if (!vars.isBlank()) {
                // ㅇㅁㅇ 변수1, ㅇㅅㅇ 변수2, ...
                String[] vs = vars.substring(1, vars.length()-1).split("]\\[");
                for (String v : vs) {
                    StringTokenizer tokenizer = new StringTokenizer(v);
                    varType.add(tokenizer.nextToken());
                    values.add(tokenizer.nextToken());
                }
            }
        }
        if (varType.size() != values.size()) throw new MatchException(MatchMessage.grammarError);
        return new RepositoryValue(varType, values);
    }

    //String, void 동작
    default void start(String line,
                       Map<String, Map<String, Object>> repository,
                       Map<String, Map<String, Object>> mainRepository,
                       Set<String> set,
                       Set<String> mainSet) {
        for (var work : Repository.priorityPrintWorks) {if (work.check(line)) {work.start(line);return;}} //강제 출력
        line = updateBlock(line); //괄호를 푸는 동작
        if (Controller.customGetVariableWork.check(line)) { //변수값 가져오기
            line = Controller.customGetVariableWork.start(line, repository, mainRepository, Repository.repository);}
        line = Controller.bracket.bracket(line);

        line = Setting.startString(line, repository, set);
        if (Controller.customSetVariableWork.check(line)) { //변수값 업데이트
            Controller.customSetVariableWork.start(line, repository, mainRepository, Repository.repository,
                    set, mainSet, Repository.set);return;}
        for (VariableWork work : Repository.variableWorks) {//변수 정의하는 동작
            if (work.check(line)) {work.start(line, repository, set); return;}}
        Setting.start(line, repository, set);
    }

    // uuid -> { 내용 }
    default String updateBlock(String line) {
        StringBuilder builder = new StringBuilder();
        uuidMap.keySet().forEach(v -> builder.append(v).append("|"));
        builder.deleteCharAt(builder.length()-1);

        while (Pattern.compile(builder.toString()).matcher(line).find()) {
            for (Map.Entry<String, String> entry : uuidMap.entrySet())
                line = line.replace(entry.getKey(), "{\n" + entry.getValue() + "\n}");
        }
        return line;
    }
}
