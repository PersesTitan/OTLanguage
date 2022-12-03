package work.v4;

import bin.apply.Repository;
import bin.calculator.tool.Calculator;
import bin.check.VariableType;
import bin.check.VariableTypeCheck;
import bin.exception.VariableException;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static bin.check.VariableCheck.*;
import static bin.token.Token.ACCESS;
import static bin.token.cal.BoolToken.TRUE;

public interface Casting extends Calculator {
    default Object casting(VariableType type, String value) {
        return switch (type) {
            case Boolean -> Calculator.getBool(value, Repository.repository);
            case Character -> isCharacter(value)
                    ? getCharacter(value)
                    : (char) getInteger(getNumberStr(value));
            case Integer -> isCharacter(value)
                    ? (int) getCharacter(value)
                    : (int) getNumber(value);
            case Long -> isCharacter(value)
                    ? (long) getCharacter(value)
                    : (long) getNumber(value);
            case Double -> getNumber(value, Repository.repository);
            case Float -> (float) getNumber(value, Repository.repository);
            case String -> value;
            case ListBoolean -> getListBoolType(type, value);
            case SetBoolean -> getSetBoolType(type, value);
            case MapBoolean -> getMapBoolType(type, value);
            case ListCharacter, SetInteger, ListDouble, ListFloat,
                    ListInteger, ListLong, ListString, MapCharacter,
                    MapDouble, MapFloat, MapInteger, MapLong, MapString,
                    SetCharacter, SetDouble, SetFloat, SetLong, SetString,
                    Method -> getListType(type, value);
        };
    }

    private double getNumber(String value) {
        return getNumber(value, Repository.repository);
    }

    private String getNumberStr(String value) {
        return getNumberStr(value, Repository.repository);
    }

    private Object getListType(VariableType type, String value) {
        if (isListString(value) || isMapString(value))
            return VariableTypeCheck.getInstance().getObject(type, value, null);
        else return VariableTypeCheck.getInstance().getObject(type, value, getVariableValue(value));
    }

    private Object getVariableValue(String value) {
        int len = accessCount(value);
        value = value.substring(len);
        var repository = Repository.repository.get(len);
        for (var rep : repository.entrySet()) {
            if (rep.getValue().containsKey(value)) return rep.getValue().get(value);
        }
        throw new VariableException().typeMatch();
    }

    private int accessCount(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ACCESS.charAt(0)) count++;
            else break;
        }
        return  count >= Repository.repository.size() ? -1 : count;
    }

    // bool
    private Object getListBoolType(VariableType type, String value) {
        Object ob = getListType(type, value);
        if (ob instanceof LinkedList<?> list) {
            return list.stream()
                    .map(Object::toString)
                    .map(v -> v.equals(TRUE))
                    .collect(Collectors.toCollection(LinkedList::new));
        } else throw new VariableException().typeMatch();
    }

    private Object getSetBoolType(VariableType type, String value) {
        Object ob = getListType(type, value);
        if (ob instanceof LinkedHashSet<?> set) {
            return set.stream()
                    .map(Object::toString)
                    .map(v -> v.equals(TRUE))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else throw new VariableException().typeMatch();
    }

    private Object getMapBoolType(VariableType type, String value) {
        Object ob = getListType(type, value);
        if (ob instanceof LinkedHashMap<?,?> map) {
            return new LinkedHashMap<String, Boolean>() {{
                map.forEach((k, v) -> this.put(k.toString(), v.toString().equals(TRUE)));
            }};
        } else throw new VariableException().typeMatch();
    }
}
