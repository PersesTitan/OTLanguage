package custom.use.controller;

import custom.registration.define.RepositoryValue;
import custom.use.define.BracketSplit;
import event.Controller;
import origin.variable.model.Repository;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseCustomString implements BracketSplit {
    public final Map<String, Map<String, Object>> repository = new HashMap<>();
    public final Set<String> set = new HashSet<>();

    private final Map<String, Map<String, Object>> mainRepository;
    private final Set<String> mainSet;

    private final RepositoryValue repositoryValue;
    private final String returnValue; //리턴하는 메소드명
    private final String patternCount; //[안녕하세요][1234] 페턴
    private final String methodName;

    private final String patternText;
    private final Pattern pattern;

    private final String total;

    public UseCustomString(String className, String methodName, String total,
                           RepositoryValue repositoryValue,
                           Map<String, Map<String, Object>> mainRepository,
                           Set<String> mainSet, String returnValue) {
        this.total = total;
        this.repositoryValue = repositoryValue;
        this.mainRepository = mainRepository;
        this.mainSet = mainSet;
        this.methodName = methodName;
        this.returnValue = returnValue;

        int count = repositoryValue.varType().size();
        //:클래스명~메소드명[안녕하세요][1234](_ )
        this.patternText = ":" + className + "~" + methodName + "\\[[^\\[\\]]+]".repeat(count) + "[_ ]";
        this.patternCount = "\\[[^\\[\\]]+]".repeat(count);
        this.pattern = Pattern.compile(this.patternText);
        //저장소 새로 만들기
        Repository.repository.keySet().forEach(key -> repository.put(key, new HashMap<>()));
    }

    public UseCustomString(String methodName, String total,
                           RepositoryValue repositoryValue,
                           Map<String, Map<String, Object>> mainRepository,
                           Set<String> mainSet, String returnValue) {
        this.total = total;
        this.repositoryValue = repositoryValue;
        this.mainRepository = mainRepository;
        this.mainSet = mainSet;
        this.methodName = methodName;
        this.returnValue = returnValue;

        int count = repositoryValue.varType().size();
        //:메소드명[안녕하세요][1234](_ )
        this.patternText = ":" + methodName + "\\[[^\\[\\]]+]".repeat(count) + "[_ ]";
        this.patternCount = "\\[[^\\[\\]]+]".repeat(count);
        this.pattern = Pattern.compile(this.patternText);
        //저장소 새로 만들기
        Repository.repository.keySet().forEach(key -> repository.put(key, new HashMap<>()));
    }

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public String start(String line) {
        // [안녕하세요][1234]
        Matcher matcher = Pattern.compile(patternCount).matcher(line);
        // getVariables : [안녕하세요][1234]
        if (matcher.find()) getVariables(matcher.group(), repositoryValue, repository, set); //초기값 변수를 정의하는 동작

        Arrays.stream(this.total.split("\\n")).forEach(this::settingStart);
        Matcher m = pattern.matcher(line);
        while (m.find()) {
            String value = Controller.customGetVariableWork.start(":"+this.returnValue+"_",
                    repository, mainRepository, Repository.repository); //변수값 가져오기
            line = line.replaceFirst(this.patternText, value);
        }
        return line;
    }

    //실행 동작
    private void settingStart(String line) {
        start(line, repository, mainRepository, set, mainSet);
    }
}
