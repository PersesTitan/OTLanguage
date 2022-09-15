package bin.orign.variable.origin.put;

import bin.apply.Repository;
import bin.exception.VariableException;
import bin.orign.variable.list.get.GetList;
import bin.orign.variable.map.get.GetMap;
import bin.orign.variable.set.get.GetSet;
import bin.token.Token;
import bin.token.VariableToken;
import work.StartWork;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.check.VariableCheck.*;

public class PutVariable implements
        StartWork, Token, VariableToken, GetSet, GetList, GetMap {
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
            int accessCount = accessCount(group);
            if (accessCount > repositoryArray.length) throw VariableException.localNoVariable();
            String variableName = group.substring(accessCount, group.length()-1); // 변수명
            var repository = repositoryArray[accessCount];
            if (Repository.noUse.contains(variableName)) throw VariableException.reservedWorks();
            else if (!Repository.getSet(repository).contains(variableName)) throw VariableException.noDefine();
            String varType = getVariableType(repository, variableName); // ㅇㅅㅇ, ㅇㅈㅇ
            if (!getType(varType, value)) throw VariableException.typeMatch();
            if (ORIGIN_LIST.contains(variableName)) repository.get(varType).put(variableName, value);
            else if (SET_LIST.contains(varType)) {
                Object ob = repository.get(varType).get(variableName);
                setSet(varType, ob, value);
            } else if (LIST_LIST.contains(varType)) {
                Object ob = repository.get(varType).get(variableName);
                setList(varType, ob, value);
            } else if (MAP_LIST.contains(varType)) {
                Object ob = repository.get(varType).get(variableName);
                setMap(varType, ob, value);
            }
        }
    }

    private String getVariableType(Map<String, Map<String, Object>> repository, String variableName) {
        for (var variableType : repository.entrySet()) {
            if (variableType.getValue().containsKey(variableName)) return variableType.getKey();
        }
        throw VariableException.sameVariable();
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

            case SET_INTEGER, LIST_INTEGER -> isListInteger(value);
            case SET_LONG, LIST_LONG -> isListLong(value);
            case SET_BOOLEAN, LIST_BOOLEAN -> isListBoolean(value);
            case SET_STRING, LIST_STRING -> isListString(value);
            case SET_CHARACTER, LIST_CHARACTER -> isListCharacter(value);
            case SET_FLOAT, LIST_FLOAT -> isListFloat(value);

            case MAP_INTEGER -> isMapInteger(value);
            case MAP_LONG -> isMapLong(value);
            case MAP_BOOLEAN -> isMapBoolean(value);
            case MAP_STRING -> isMapString(value);
            case MAP_CHARACTER -> isMapCharacter(value);
            case MAP_FLOAT -> isMapFloat(value);
            case MAP_DOUBLE -> isMapDouble(value);

            default -> true;
        };
    }

    private void setSet(String variableType, Object ob, String value) {
        switch (variableType) {
            case SET_INTEGER -> {
                Set<Integer> set = (Set<Integer>) ob;
                set.clear();
                set.addAll(getIntegerSet(value));
            }
            case SET_LONG -> {
                Set<Long> set = (Set<Long>) ob;
                set.clear();
                set.addAll(getLongSet(value));
            }
            case SET_BOOLEAN -> {
                Set<String> set = (Set<String>) ob;
                set.clear();
                set.addAll(getBoolSet(value));
            }
            case SET_STRING -> {
                Set<String> set = (Set<String>) ob;
                set.clear();
                set.addAll(getStringSet(value));
            }
            case SET_CHARACTER -> {
                Set<Character> set = (Set<Character>) ob;
                set.clear();
                set.addAll(getCharacterSet(value));
            }
            case SET_FLOAT -> {
                Set<Float> set = (Set<Float>) ob;
                set.clear();
                set.addAll(getFlotSet(value));
            }
        }
    }

    private void setList(String variableType, Object ob, String value) {
        switch (variableType) {
            case LIST_INTEGER -> {
                Set<Integer> set = (Set<Integer>) ob;
                set.clear();
                set.addAll(getIntegerList(value));
            }
            case LIST_LONG -> {
                Set<Long> set = (Set<Long>) ob;
                set.clear();
                set.addAll(getLongList(value));
            }
            case LIST_BOOLEAN -> {
                Set<String> set = (Set<String>) ob;
                set.clear();
                set.addAll(getBoolList(value));
            }
            case LIST_STRING -> {
                Set<String> set = (Set<String>) ob;
                set.clear();
                set.addAll(getStringList(value));
            }
            case LIST_CHARACTER -> {
                Set<Character> set = (Set<Character>) ob;
                set.clear();
                set.addAll(getCharacterList(value));
            }
            case LIST_FLOAT -> {
                Set<Float> set = (Set<Float>) ob;
                set.clear();
                set.addAll(getFlotList(value));
            }
            case LIST_DOUBLE -> {
                Set<Double> set = (Set<Double>) ob;
                set.clear();
                set.addAll(getDoubleList(value));
            }
        }
    }

    private void setMap(String variableType, Object ob, String value) {
        switch (variableType) {
            case MAP_INTEGER -> {
                Map<String, Integer> map = (Map<String, Integer>) ob;
                map.clear();
                map.putAll(getIntegerMap(value));
            }
            case MAP_LONG -> {
                Map<String, Long> map = (Map<String, Long>) ob;
                map.clear();
                map.putAll(getLongMap(value));
            }
            case MAP_BOOLEAN -> {
                Map<String, String> map = (Map<String, String>) ob;
                map.clear();
                map.putAll(getBoolMap(value));
            }
            case MAP_STRING -> {
                Map<String, String> map = (Map<String, String>) ob;
                map.clear();
                map.putAll(getStringMap(value));
            }
            case MAP_CHARACTER -> {
                Map<String, Character> map = (Map<String, Character>) ob;
                map.clear();
                map.putAll(getCharacterMap(value));
            }
            case MAP_FLOAT -> {
                Map<String, Float> map = (Map<String, Float>) ob;
                map.clear();
                map.putAll(getFlotMap(value));
            }
            case MAP_DOUBLE -> {
                Map<String, Double> map = (Map<String, Double>) ob;
                map.clear();
                map.putAll(getDoubleMap(value));
            }
        }
    }
}
