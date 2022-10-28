package bin.orign;

import bin.orign.variable.list.get.GetList;
import bin.orign.variable.map.get.GetMap;
import bin.orign.variable.set.get.GetSet;
import bin.token.LoopToken;

import java.util.LinkedHashMap;
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
                set.addAll(setIntegerSet(set, value));
            }
            case SET_LONG -> {
                LinkedHashSet<Long> set = (LinkedHashSet<Long>) ob;
                set.clear();
                set.addAll(setLongSet(set, value));
            }
            case SET_BOOLEAN -> {
                LinkedHashSet<String> set = (LinkedHashSet<String>) ob;
                set.clear();
                set.addAll(setBoolSet(set, value));
            }
            case SET_STRING -> {
                LinkedHashSet<String> set = (LinkedHashSet<String>) ob;
                set.clear();
                set.addAll(setStringSet(set, value));
            }
            case SET_CHARACTER -> {
                LinkedHashSet<Character> set = (LinkedHashSet<Character>) ob;
                set.clear();
                set.addAll(setCharacterSet(set, value));
            }
            case SET_FLOAT -> {
                LinkedHashSet<Float> set = (LinkedHashSet<Float>) ob;
                set.clear();
                set.addAll(setFlotSet(set, value));
            }
        }
    }

    public void setList(String variableType, Object ob, String value) {
        switch (variableType) {
            case LIST_INTEGER -> {
                LinkedList<Integer> set = (LinkedList<Integer>) ob;
                set.clear();
                set.addAll(setIntegerList(set, value));
            }
            case LIST_LONG -> {
                LinkedList<Long> set = (LinkedList<Long>) ob;
                set.clear();
                set.addAll(setLongList(set, value));
            }
            case LIST_BOOLEAN -> {
                LinkedList<String> set = (LinkedList<String>) ob;
                set.clear();
                set.addAll(setBoolList(set, value));
            }
            case LIST_STRING -> {
                LinkedList<String> set = (LinkedList<String>) ob;
                set.clear();
                set.addAll(setStringList(set, value));
            }
            case LIST_CHARACTER -> {
                LinkedList<Character> set = (LinkedList<Character>) ob;
                set.clear();
                set.addAll(setCharacterList(set, value));
            }
            case LIST_FLOAT -> {
                LinkedList<Float> set = (LinkedList<Float>) ob;
                set.clear();
                set.addAll(setFlotList(set, value));
            }
            case LIST_DOUBLE -> {
                LinkedList<Double> set = (LinkedList<Double>) ob;
                set.clear();
                set.addAll(setDoubleList(set, value));
            }
        }
    }

    public void setMap(String variableType, Object ob, String value) {
        switch (variableType) {
            case MAP_INTEGER -> {
                LinkedHashMap<String, Integer> map = (LinkedHashMap<String, Integer>) ob;
                map.clear();
                setIntegerMap(map, value);
            }
            case MAP_LONG -> {
                LinkedHashMap<String, Long> map = (LinkedHashMap<String, Long>) ob;
                map.clear();
                setLongMap(map, value);
            }
            case MAP_BOOLEAN -> {
                LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) ob;
                map.clear();
                setBoolMap(map, value);
            }
            case MAP_STRING -> {
                LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) ob;
                map.clear();
                setStringMap(map, value);
            }
            case MAP_CHARACTER -> {
                LinkedHashMap<String, Character> map = (LinkedHashMap<String, Character>) ob;
                map.clear();
                setCharacterMap(map, value);
            }
            case MAP_FLOAT -> {
                LinkedHashMap<String, Float> map = (LinkedHashMap<String, Float>) ob;
                map.clear();
                setFlotMap(map, value);
            }
            case MAP_DOUBLE -> {
                LinkedHashMap<String, Double> map = (LinkedHashMap<String, Double>) ob;
                map.clear();
                setDoubleMap(map, value);
            }
        }
    }
}
