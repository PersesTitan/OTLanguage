package origin.variable.controller;

import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.variable.define.VariableCheck;
import origin.variable.define.VariableType;
import origin.variable.model.Repository;
import origin.variable.model.VariableWork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeListVariable implements VariableWork, Repository {
    private final String variable;  //ㅇㅅㅇ, ㅇㅁㅇ, ...
    private final Pattern pattern; //ㄹㅁㄹ, ㄹㅅㄹ
    private final VariableType varType;  //Integer, Long, ...

    public MakeListVariable(String variable, VariableType varType) {
        this.variable = variable;
        this.varType = varType;
        String varPattern = "^\\s*" + variable + "\\s+[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+";
        this.pattern = Pattern.compile(varPattern);
        repository.put(variable, new HashMap<>());
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        String patternText = "^\\s*" + variable + "\\s+";
        Pattern pattern = Pattern.compile(patternText); // ㄹㅁㄹ, ㅇㅁㅇ, ...
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String type = matcher.group().strip();  //ㄹㅁㄹ, ㅇㅁㅇ, ...
            if (!repository.containsKey(type)) throw new VariableException(type + VariableMessage.doNotDefine);
            String value = line.replaceFirst(patternText, "").strip(); //변수명

            if (value.isBlank()) throw new VariableException(VariableMessage.noHaveVarName);
            if (set.contains(value)) throw new VariableException(type + VariableMessage.sameVariable);
            //저장소에 값 저장
            repository.get(type).put(value, new ArrayList<>());
            set.add(value);
        }
    }
}
