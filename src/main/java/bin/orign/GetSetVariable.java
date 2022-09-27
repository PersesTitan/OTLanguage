package bin.orign;

import bin.orign.variable.list.get.GetList;
import bin.orign.variable.map.get.GetMap;
import bin.orign.variable.set.get.GetSet;
import bin.token.LoopToken;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.*;

public class GetSetVariable implements GetSet, GetList, GetMap, LoopToken {
    // variableName = ㅇㅅㅇ, ㅇㅂㅇ, ㅇㅁㅇ
    // value = 1234, 문자
    public boolean getType(String variableName, String value) {
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

    public void setSet(String variableType, Object ob, String value) {
        switch (variableType) {
            case SET_INTEGER -> {
                LinkedHashSet<Integer> set = (LinkedHashSet<Integer>) ob;
                set.clear();
                set.addAll(getIntegerSet(value));
            }
            case SET_LONG -> {
                LinkedHashSet<Long> set = (LinkedHashSet<Long>) ob;
                set.clear();
                set.addAll(getLongSet(value));
            }
            case SET_BOOLEAN -> {
                LinkedHashSet<String> set = (LinkedHashSet<String>) ob;
                set.clear();
                set.addAll(getBoolSet(value));
            }
            case SET_STRING -> {
                LinkedHashSet<String> set = (LinkedHashSet<String>) ob;
                set.clear();
                set.addAll(getStringSet(value));
            }
            case SET_CHARACTER -> {
                LinkedHashSet<Character> set = (LinkedHashSet<Character>) ob;
                set.clear();
                set.addAll(getCharacterSet(value));
            }
            case SET_FLOAT -> {
                LinkedHashSet<Float> set = (LinkedHashSet<Float>) ob;
                set.clear();
                set.addAll(getFlotSet(value));
            }
        }
    }

    public void setList(String variableType, Object ob, String value) {
        switch (variableType) {
            case LIST_INTEGER -> {
                LinkedList<Integer> set = (LinkedList<Integer>) ob;
                set.clear();
                set.addAll(getIntegerList(value));
            }
            case LIST_LONG -> {
                LinkedList<Long> set = (LinkedList<Long>) ob;
                set.clear();
                set.addAll(getLongList(value));
            }
            case LIST_BOOLEAN -> {
                LinkedList<String> set = (LinkedList<String>) ob;
                set.clear();
                set.addAll(getBoolList(value));
            }
            case LIST_STRING -> {
                LinkedList<String> set = (LinkedList<String>) ob;
                set.clear();
                set.addAll(getStringList(value));
            }
            case LIST_CHARACTER -> {
                LinkedList<Character> set = (LinkedList<Character>) ob;
                set.clear();
                set.addAll(getCharacterList(value));
            }
            case LIST_FLOAT -> {
                LinkedList<Float> set = (LinkedList<Float>) ob;
                set.clear();
                set.addAll(getFlotList(value));
            }
            case LIST_DOUBLE -> {
                LinkedList<Double> set = (LinkedList<Double>) ob;
                set.clear();
                set.addAll(getDoubleList(value));
            }
        }
    }

    public void setMap(String variableType, Object ob, String value) {
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
