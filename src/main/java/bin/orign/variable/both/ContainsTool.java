package bin.orign.variable.both;

import bin.check.VariableType;
import bin.exception.VariableException;
import org.apache.hadoop.hdfs.protocol.datatransfer.sasl.DataEncryptionKeyFactory;

import java.util.*;

import static bin.check.VariableCheck.isInteger;
import static bin.check.VariableTypeCheck.getVariableType;
import static bin.token.VariableToken.*;
import static bin.token.VariableToken.LIST_DOUBLE;
import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public interface ContainsTool {
    default String collectionCheck(Collection<?> collection, String variableType, String value) {
        return collection.contains(getVariable(getVariableType(variableType), value)) ? TRUE : FALSE;
    }

    // variableType : 변수 타입
    // value : 값
    private Object getVariable(VariableType variableType, String value) {
        // Boolean, Character, Double, Float, Integer, Long, String
        String variable = variableType.name();
        try {
            if (variable.startsWith("Map")) return value;
            else if (variable.endsWith("Character")) {
                if (value.length() != 1) throw VariableException.typeMatch();
                else return value.charAt(0);
            } else if (variable.endsWith("Double")) return Double.parseDouble(value);
            else if (variable.endsWith("Float")) return Float.parseFloat(value);
            else if (variable.endsWith("Integer")) return Integer.parseInt(value);
            else if (variable.endsWith("Long")) return Long.parseLong(value);
            else return value;
        } catch (Exception e) {throw VariableException.typeMatch();}
    }

    // 리스트, 셋 종합 구하기
    default String collectionSum(Collection<?> collection, String variableType) {
        return getSum(getVariableType(variableType), collection);
    }

    private String getSum(VariableType variableType, Collection<?> collection) {
        // Boolean, Character, Double, Float, Integer, Long, String
        String variable = variableType.name();
        try {
            if (variable.endsWith("Float")) return String.valueOf(collection.stream().mapToDouble(v -> (float) v).sum());
            else if (variable.endsWith("Double")) return String.valueOf(collection.stream().mapToDouble(v -> (double) v).sum());
            else if (variable.endsWith("Integer")) return String.valueOf(collection.stream().mapToInt(v -> (int) v).sum());
            else if (variable.endsWith("Long")) return String.valueOf(collection.stream().mapToLong(v -> (long) v).sum());
            throw VariableException.typeMatch();
        } catch (Exception e) {throw VariableException.typeMatch();}
    }

    // Start
    // Set sort
    default void sortSet(LinkedHashSet<Object> set) {
        TreeSet<Object> treeSet = new TreeSet<>(set);
        set.clear();
        set.addAll(treeSet);
    }

    default void sortList(String type, Object ob) {
        switch (type) {
            case LIST_INTEGER -> Collections.sort((LinkedList<Integer>) ob);
            case LIST_LONG -> Collections.sort((LinkedList<Long>) ob);
            case LIST_BOOLEAN -> Collections.sort((LinkedList<String>) ob);
            case LIST_CHARACTER -> Collections.sort((LinkedList<Character>) ob);
            case LIST_FLOAT -> Collections.sort((LinkedList<Float>) ob);
            case LIST_DOUBLE -> Collections.sort((LinkedList<Double>) ob);
            default -> Collections.sort((LinkedList<String>) ob);
        }
    }

    // Delete
    default void delete(Collection<Object> set, String variableType, String value) {
        // Boolean, Character, Double, Float, Integer, Long, String
        String variable = getVariableType(variableType).name();
        try {
            if (variable.startsWith("Map")) set.remove(value);
            else if (variable.endsWith("Character")) {
                if (value.length() != 1) throw VariableException.typeMatch();
                else set.remove(value.charAt(0));
            } else if (variable.endsWith("Double")) set.remove(Double.parseDouble(value));
            else if (variable.endsWith("Float")) set.remove(Float.parseFloat(value));
            else if (variable.endsWith("Integer")) set.remove(Integer.parseInt(value));
            else if (variable.endsWith("Long")) set.remove(Long.parseLong(value));
            else set.remove(value);
        } catch (Exception e) {throw VariableException.typeMatch();}
    }

    // value get
    default String getSet(LinkedHashSet<Object> set, String key) {
        int count = Integer.parseInt(key);
        if (set.size() > count) return set.stream().toList().get(count).toString();
        else return null;
    }

    default String getList(LinkedList<Object> list, String key) {
        int count = Integer.parseInt(key);
        if (list.size() > count) return list.get(count).toString();
        else return null;
    }

    // map one add
    default void addMap(LinkedHashMap<String, Object> map, String value) {
        String[] values = value.split(MAP_ADD, 2);
        map.put(values[0], values[1]);
    }
}
