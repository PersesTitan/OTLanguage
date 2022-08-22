package origin.variable.controller.set;

import event.Setting;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.define.VariableType;
import origin.variable.model.Repository;
import origin.variable.model.VariableWork;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeSetVariable implements VariableWork, Repository {
    private final String patternText;  //ㅇㅅㅇ, ㅇㅁㅇ, ...
    private final Pattern pattern; //ㄹㅁㄹ, ㄹㅅㄹ
    private final VariableType varType;  //Integer, Long, ...

    public MakeSetVariable(String patternText, VariableType variableType) {
        this.patternText = patternText;
        this.varType = variableType;
        // ㅈㅈㅈ 변수명
        String varPattern = "^\\s*" + patternText + "\\s+"+ Setting.variableStyle;
        this.pattern = Pattern.compile(varPattern);
        repository.put(patternText, new HashMap<>());
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line,
                      Map<String, Map<String, Object>> repository,
                      Set<String> set) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group().strip();
            StringTokenizer tokenizer = new StringTokenizer(group);
            String variableType = tokenizer.nextToken(); // ㅈㅁㅈ
            String name = tokenizer.nextToken(); // 변수명
            if (variableType == null) throw new MatchException(MatchMessage.grammarError);
            if (name == null) throw new MatchException(MatchMessage.grammarError);
            if (name.isBlank()) throw new VariableException(VariableMessage.noHaveVarName);
            if (set.contains(name)) throw new VariableException(name + VariableMessage.doNotFind);
            //저장소에 값 저장
            repository.get(variableType).put(name, new LinkedHashSet<>());
            set.add(name);
        }
    }
}
