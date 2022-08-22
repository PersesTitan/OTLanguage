package custom.use.controller;

import custom.registration.define.RepositoryValue;
import custom.use.define.BracketSplit;
import event.Controller;
import event.Setting;
import origin.variable.model.Repository;
import origin.variable.model.VariableWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseCustomVoid implements BracketSplit {
    public final Map<String, Map<String, Object>> repository = new HashMap<>();
    public final Set<String> set = new HashSet<>();

    private final Map<String, Map<String, Object>> mainRepository;
    private final Set<String> mainSet;

    private final RepositoryValue repositoryValue;
    private final String patternCount; //[안녕하세요][1234] 페턴
    private final String methodName;

    private final String patternText;
    private final Pattern pattern;

    private final String total;

    public UseCustomVoid(String className, String methodName, String total,
                         RepositoryValue repositoryValue,
                         Map<String, Map<String, Object>> mainRepository,
                         Set<String> mainSet) {
        this.total = total;
        this.repositoryValue = repositoryValue;
        this.mainRepository = mainRepository;
        this.mainSet = mainSet;
        this.methodName = methodName;

        int count = repositoryValue.values().size();
        //클래스명~메소드명[안녕하세요][1234]
        this.patternText = "^\\s*" + className + "~" + methodName + "\\[[^\\[\\]]+]".repeat(count);
        this.patternCount = "\\[[^\\[\\]]+]".repeat(count);
        this.pattern = Pattern.compile(this.patternText);
        //저장소 새로 만들기
        Repository.repository.keySet().forEach(key -> repository.put(key, new HashMap<>()));
    }

    public UseCustomVoid(String methodName, String total,
                         RepositoryValue repositoryValue,
                         Map<String, Map<String, Object>> mainRepository,
                         Set<String> mainSet) {
        this.total = total;
        this.repositoryValue = repositoryValue;
        this.mainRepository = mainRepository;
        this.mainSet = mainSet;
        this.methodName = methodName;

        int count = repositoryValue.values().size();
        //메소드명[안녕하세요][1234]
        this.patternText = "^\\s*" + methodName + "\\[[^\\[\\]]+]".repeat(count);
        this.patternCount = "\\[[^\\[\\]]+]".repeat(count);
        this.pattern = Pattern.compile(this.patternText);
        //저장소 새로 만들기
        Repository.repository.keySet().forEach(key -> repository.put(key, new HashMap<>()));
    }

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public void start(String line) {
        // [안녕하세요][1234]
        Matcher matcher = Pattern.compile(patternCount).matcher(line);
        // getVariables : [안녕하세요][1234]
        if (matcher.find()) getVariables(matcher.group(), repositoryValue, repository, set); //초기값 변수를 정의하는 동작

        Arrays.stream(this.total.split("\\n")).forEach(this::settingStart);
    }

    //실행 동작
    private void settingStart(String line) {
        start(line, repository, mainRepository, set, mainSet);
    }
}
