package origin.variable;

import http.variable.HttpWork;
import origin.exception.VariableException;
import origin.exception.VariableMessage;
import origin.item.VarCheck;
import origin.item.kind.VarType;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeVariable implements HttpWork, VariableRepository {
    private final VarCheck varCheck = new VarCheck();
    private final String variable;  //ㅇㅅㅇ, ㅇㅁㅇ, ...
    private final VarType varType;  //Integer, Long, ...
    private final Pattern pattern;

    public MakeVariable(String variable, VarType varType) {
        this.variable = variable;
        this.varType = varType;
        String varPattern = "^\\s*" + variable + "\\s+[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+:";
        this.pattern = Pattern.compile(varPattern);
        varRep.put(variable, new HashMap<>());
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) throws Exception {
        String patternText = "^\\s*" + variable + "\\s+";
        Pattern pattern = Pattern.compile(patternText); // ㅇㅅㅇ, ㅇㅁㅇ, ...
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String type = matcher.group().strip();  //ㅇㅅㅇ, ㅇㅁㅇ, ...
            if (!varRep.containsKey(type)) throw new VariableException(type + VariableMessage.doNotDefine);
            //변수:초기값 => 변수 초기값
            StringTokenizer tokenizer = new StringTokenizer(line.replaceAll(patternText, ""), ":");
            String key = tokenizer.nextToken(); //변수
            String value = tokenizer.nextToken(); //초기값
            if (value == null) throw new VariableException(VariableMessage.noHaveInitial);
            if (set.contains(key)) throw new VariableException(key + VariableMessage.sameVariable);
            if (!varCheck.check(value, varType)) throw new VariableException(VariableMessage.typeMatchError);
            //저장소에 값 저장
            varRep.get(type).put(key, value);
            set.add(key);
        }
    }
}
