package origin.variable;

import origin.exception.VariableException;
import origin.exception.VariableMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetVariable implements VariableRepository {
    //:변수명( )
    String patternText = ":[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_-]+( )";
    Pattern pattern = Pattern.compile(patternText);

    public String getVariable(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String key = matcher.group().substring(1).trim(); //:변수명 => 변수명
            if (!set.contains(key)) throw new VariableException(key + VariableMessage.doNotFind);
            for (String keys : varRep.keySet()) {
                if (varRep.get(keys).containsKey(key)) {
                    line = line.replaceAll(patternText, varRep.get(keys).get(key).toString());
                    break;
                }
            }
        }
        return line;
    }

    public boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
