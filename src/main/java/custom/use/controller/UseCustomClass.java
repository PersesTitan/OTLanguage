package custom.use.controller;

import custom.registration.controller.DefineCustomMethod;
import custom.registration.define.RepositoryValue;
import custom.use.define.BracketSplit;
import event.Controller;
import event.Setting;
import origin.consol.define.PriorityPrintWork;
import origin.variable.model.Repository;
import origin.variable.model.VariableWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseCustomClass implements BracketSplit {
    public final Map<String, Map<String, Object>> repository = new HashMap<>();
    public final Set<String> set = new HashSet<>();
    public final Set<String> methods = new HashSet<>(); //메소드명 저장
    //메소드 저장소
    public final Set<UseCustomString> useCustomStrings = new HashSet<>();
    public final Set<UseCustomVoid> useCustomVoids = new HashSet<>();

    public final String className;

    private final RepositoryValue repositoryValue;
    private final String patternText; //클래스명[안녕하세요][1234]
    private final String patternCount; //[안녕하세요][1234] 페턴
    private final Pattern pattern;
    private final String total; //동작 가져오기
    private final DefineCustomMethod defineCustomMethod;

    public UseCustomClass(String className, RepositoryValue repositoryValue, String total) {
        this.className = className;
        this.total = total;
        this.repositoryValue = repositoryValue;
        int count = repositoryValue.varType().size();
        this.patternText = "^\\s*" + className + "\\[[^\\[\\]]+]".repeat(count);
        this.patternCount = "\\[[^\\[\\]]+]".repeat(count);
        this.pattern = Pattern.compile(this.patternText);
        //저장소 새로 만들기
        Repository.repository.keySet().forEach(key -> repository.put(key, new HashMap<>()));
        this.defineCustomMethod = new DefineCustomMethod("ㅁㅅㅁ", this);
    }

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public void start(String line) {
        // [안녕하세요][1234]
        Matcher matcher = Pattern.compile(patternCount).matcher(line);
        // getVariables : [안녕하세요][1234]
        if (matcher.find()) getVariables(matcher.group(), repositoryValue, repository, set); //초기값 변수를 정의하는 동작
        Arrays.stream(total.split("\\n")).forEach(this::settingStart);
    }

    private void settingStart(String line) {
        for (var work : Repository.priorityPrintWorks) {if (work.check(line)) {work.start(line);return;}} //강제 출력
        line = updateBlock(line); //괄호를 푸는 동작
        // 변수 세팅 등을 하는 동작
        if (Controller.customGetVariableWork.check(line)) { //변수값 가져오기
            line = Controller.customGetVariableWork.start(line, repository, Repository.repository);}
        line = Controller.bracket.bracket(line);
        line = Setting.startString(line);
        if (this.defineCustomMethod.check(line)) {this.defineCustomMethod.start(line); return;} // 메소드 생성
        if (Controller.customSetVariableWork.check(line)) { //변수값 업데이트
            Controller.customSetVariableWork.start(line, repository, Repository.repository, set, Repository.set);return;}
        for (VariableWork work : Repository.variableWorks) {//변수 정의하는 동작
            if (work.check(line)) {work.start(line, repository, set); return;}}
        Setting.start(line);
    }
}
