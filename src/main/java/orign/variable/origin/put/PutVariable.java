package orign.variable.origin.put;

import apply.Repository;
import exception.VariableException;
import token.Token;
import token.VariableToken;
import work.StartWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static apply.Repository.noUse;
import static check.VariableCheck.*;

public class PutVariable implements StartWork, Token, VariableToken {
    private final String patternText = startMerge(VARIABLE_SET);
    private final Pattern pattern = Pattern.compile(patternText);
    private final Pattern accessPattern = Pattern.compile(ACCESS);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
        line = line.strip();
        String value = line.replaceFirst(patternText, ""); //넣을 값
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group();
            int accessCount = countAccess(group);
            if (accessCount > repositoryArray.length) throw VariableException.localNoVariable();
            String variableName = group.substring(accessCount, group.length()-1); // 변수명
            var repository = repositoryArray[accessCount];
            if (noUse.contains(variableName)) throw VariableException.reservedWorks();
            else if (!Repository.getSet(repository).contains(variableName)) throw VariableException.noDefine();
            String varType = getVariableType(repository, variableName); // ㅇㅅㅇ, ㅇㅈㅇ
            if (!getType(varType, value)) throw VariableException.typeMatch();
            repository.get(varType).put(variableName, value);
        }
    }

    private String getVariableType(Map<String, Map<String, Object>> repository, String variableName) {
        for (var variableType : repository.entrySet()) {
            if (variableType.getValue().containsKey(variableName)) return variableType.getKey();
        }
        throw VariableException.sameVariable();
    }

    private int countAccess(String line) {
        int count = 0;
        for (int i = 0; i<line.length(); i++) {
            if (line.charAt(i) == ACCESS.charAt(0)) count++;
            else break;
        }
        return count;
    }

    // variableName = ㅇㅅㅇ, ㅇㅂㅇ, ㅇㅁㅇ
    // value = 1234, 문자
    private boolean getType(String variableName, String value) {
        return switch (variableName) {
            case INT_VARIABLE -> isInteger(value);
            case LONG_VARIABLE -> isLong(value);
            case BOOL_VARIABLE -> isBoolean(value);
            case CHARACTER_VARIABLE -> isCharacter(value);
            case FLOAT_VARIABLE -> isFloat(value);
            case DOUBLE_VARIABLE -> isDouble(value);
            default -> true;
        };
    }
}
