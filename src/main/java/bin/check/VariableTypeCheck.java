package bin.check;

import bin.calculator.tool.Calculator;
import bin.define.item.MethodItemReturn;
import bin.define.item.MethodItemVoid;
import bin.exception.VariableException;
import bin.orign.variable.GetList;
import bin.orign.variable.GetMap;
import bin.orign.variable.GetSet;
import bin.token.VariableToken;

import java.util.*;

import static bin.check.VariableCheck.*;
import static bin.token.LoopToken.METHOD;

public class VariableTypeCheck implements VariableToken, GetSet, GetList, GetMap {
    public static final Set<VariableType> originList = new HashSet<>() {{
        add(VariableType.Integer);
        add(VariableType.Long);
        add(VariableType.Boolean);
        add(VariableType.Character);
        add(VariableType.Float);
        add(VariableType.Double);
        add(VariableType.String);
    }};

    public static VariableType getVariableType(String variableType) {
        return switch (variableType) {
            case INT_VARIABLE -> VariableType.Integer;
            case LONG_VARIABLE -> VariableType.Long;
            case BOOL_VARIABLE -> VariableType.Boolean;
            case CHARACTER_VARIABLE -> VariableType.Character;
            case FLOAT_VARIABLE -> VariableType.Float;
            case DOUBLE_VARIABLE -> VariableType.Double;
            case SET_INTEGER -> VariableType.SetInteger;
            case SET_LONG -> VariableType.SetLong;
            case SET_BOOLEAN -> VariableType.SetBoolean;
            case SET_STRING -> VariableType.SetString;
            case SET_CHARACTER -> VariableType.SetCharacter;
            case SET_FLOAT -> VariableType.SetFloat;
            case SET_DOUBLE -> VariableType.SetDouble;
            case LIST_INTEGER -> VariableType.ListInteger;
            case LIST_LONG -> VariableType.ListLong;
            case LIST_BOOLEAN -> VariableType.ListBoolean;
            case LIST_STRING -> VariableType.ListString;
            case LIST_CHARACTER -> VariableType.ListCharacter;
            case LIST_FLOAT -> VariableType.ListFloat;
            case LIST_DOUBLE -> VariableType.ListDouble;
            case MAP_INTEGER -> VariableType.MapInteger;
            case MAP_LONG -> VariableType.MapLong;
            case MAP_BOOLEAN -> VariableType.MapBoolean;
            case MAP_STRING -> VariableType.MapString;
            case MAP_CHARACTER -> VariableType.MapCharacter;
            case MAP_FLOAT -> VariableType.MapFloat;
            case MAP_DOUBLE -> VariableType.MapDouble;
            case METHOD -> VariableType.Method;
            default -> VariableType.String;
        };
    }

    // value : <value or :value
    private String getSetValue(LinkedHashSet<?> set, String value) {
        if (value.startsWith(SET_ADD)) return value.substring(SET_ADD.length()).strip();
        else if (value.startsWith(VARIABLE_PUT)) {
            set.clear();
            return value.substring(VARIABLE_PUT.length()).strip();
        } else throw new VariableException().noGrammar();
    }

    // value : <<value or :value
    private String getListValue(LinkedList<?> list, String value) {
        if (value.startsWith(LIST_ADD)) return value.substring(LIST_ADD.length()).strip();
        else if (value.startsWith(VARIABLE_PUT)) {
            list.clear();
            return value.substring(VARIABLE_PUT.length()).strip();
        } else throw new VariableException().noGrammar();
    }

    // value : <<<value or :value
    private String getMapValue(LinkedHashMap<?, ?> map, String value) {
        if (value.startsWith(MAP_ADD)) return value.substring(MAP_ADD.length()).strip();
        else if (value.startsWith(VARIABLE_PUT)) {
            map.clear();
            return value.substring(VARIABLE_PUT.length()).strip();
        } else throw new VariableException().noGrammar();
    }

    private String getValueSet(String value) {
        return value.startsWith(SET_ADD)
                ? value.substring(SET_ADD.length())
                : (value.startsWith(VARIABLE_PUT)
                    ? value.substring(VARIABLE_PUT.length())
                    : value);
    }

    private String getValueList(String value) {
        return value.startsWith(LIST_ADD)
                ? value.substring(LIST_ADD.length())
                : (value.startsWith(VARIABLE_PUT)
                ? value.substring(VARIABLE_PUT.length())
                : value);
    }

    private String getValueMap(String value) {
        return value.startsWith(MAP_ADD)
                ? value.substring(MAP_ADD.length())
                : (value.startsWith(VARIABLE_PUT)
                ? value.substring(VARIABLE_PUT.length())
                : value);
    }

    public Object getObject(VariableType variableType,
                            Object values, Object object) {
        String value = values.toString();
        switch (variableType) {
            case Method -> {
                if (values instanceof MethodItemReturn || values instanceof MethodItemVoid) return values;
                else throw new VariableException().methodTypeMatch();
            }
            case Integer -> {
                if (!isInteger(value)) throw new VariableException().typeMatch();
                else return Integer.parseInt(value);
            }
            case Long -> {
                if (!isLong(value)) throw new VariableException().typeMatch();
                else return Long.parseLong(value);
            }
            case Boolean -> {
                if (!isBoolean(value)) throw new VariableException().typeMatch();
                else return value;
            }
            case Character -> {
                if (!isCharacter(value)) throw new VariableException().typeMatch();
                else return value.charAt(0);
            }
            case Float -> {
                if (!isFloat(value)) throw new VariableException().typeMatch();
                else return Float.parseFloat(value);
            }
            case Double -> {
                if (!isDouble(value)) throw new VariableException().typeMatch();
                else return Double.parseDouble(value);
            }
            case SetInteger -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashSet<Integer>()
                        : setIntegerSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<Integer> set = (LinkedHashSet<Integer>) object;
                    return setIntegerSet(set, getSetValue(set, value));
                }
            }
            case SetLong -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashSet<Long>()
                        : setLongSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<Long> set = (LinkedHashSet<Long>) object;
                    return setLongSet(set, getSetValue(set, value));
                }
            }
            case SetBoolean -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashSet<String>()
                        : setBoolSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<String> set = (LinkedHashSet<String>) object;
                    return setBoolSet(set, getSetValue(set, value));
                }
            }
            case SetString -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashSet<String>()
                        : setStringSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<String> set = (LinkedHashSet<String>) object;
                    return setStringSet(set, getSetValue(set, value));
                }
            }
            case SetCharacter -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashSet<Character>()
                        : setCharacterSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<Character> set = (LinkedHashSet<Character>) object;
                    return setCharacterSet(set, getSetValue(set, value));
                }
            }
            case SetFloat -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashSet<Float>()
                        : setFlotSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<Float> set = (LinkedHashSet<Float>) object;
                    return setFlotSet(set, getSetValue(set, value));
                }
            }
            case SetDouble -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashSet<Double>()
                        : setDoubleSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<Double> set = (LinkedHashSet<Double>) object;
                    return setDoubleSet(set, getSetValue(set, value));
                }
            }
            case ListInteger -> {
                if (object == null) return value.isBlank()
                        ? new LinkedList<Integer>()
                        : setIntegerList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<Integer> list = (LinkedList<Integer>) object;
                    return setIntegerList(list, getListValue(list, value));
                }
            }
            case ListLong -> {
                if (object == null) return value.isBlank()
                        ? new LinkedList<Long>()
                        : setLongList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<Long> list = (LinkedList<Long>) object;
                    return setLongList(list, getListValue(list, value));
                }
            }
            case ListBoolean -> {
                if (object == null) return value.isBlank()
                        ? new LinkedList<String>()
                        : setBoolList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<String> list = (LinkedList<String>) object;
                    return setBoolList(list, getListValue(list, value));
                }
            }
            case ListString -> {
                if (object == null) return value.isBlank()
                        ? new LinkedList<String>()
                        : setStringList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<String> list = (LinkedList<String>) object;
                    return setStringList(list, getListValue(list, value));
                }
            }
            case ListCharacter -> {
                if (object == null) return value.isBlank()
                        ? new LinkedList<Character>()
                        : setCharacterList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<Character> list = (LinkedList<Character>) object;
                    return setCharacterList(list, getListValue(list, value));
                }
            }
            case ListFloat -> {
                if (object == null) return value.isBlank()
                        ? new LinkedList<Float>()
                        : setFlotList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<Float> list = (LinkedList<Float>) object;
                    return setFlotList(list, getListValue(list, value));
                }
            }
            case ListDouble -> {
                if (object == null) return value.isBlank()
                        ? new LinkedList<Double>()
                        : setDoubleList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<Double> list = (LinkedList<Double>) object;
                    return setDoubleList(list, getListValue(list, value));
                }
            }
            case MapInteger -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashMap<String, Integer>()
                        : setIntegerMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, Integer> map = (LinkedHashMap<String, Integer>) object;
                    return setIntegerMap(map, getMapValue(map, value));
                }
            }
            case MapLong -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashMap<String, Long>()
                        : setLongMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, Long> map = (LinkedHashMap<String, Long>) object;
                    return setLongMap(map, getMapValue(map, value));
                }
            }
            case MapBoolean -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashMap<String, String>()
                        : setBoolMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) object;
                    return setBoolMap(map, getMapValue(map, value));
                }
            }
            case MapString -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashMap<String, String>()
                        : setStringMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) object;
                    return setStringMap(map, getMapValue(map, value));
                }
            }
            case MapCharacter -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashMap<String, Character>()
                        : setCharacterMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, Character> map = (LinkedHashMap<String, Character>) object;
                    return setCharacterMap(map, getMapValue(map, value));
                }
            }
            case MapFloat -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashMap<String, Float>()
                        : setFlotMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, Float> map = (LinkedHashMap<String, Float>) object;
                    return setFlotMap(map, getMapValue(map, value));
                }
            }
            case MapDouble -> {
                if (object == null) return value.isBlank()
                        ? new LinkedHashMap<String, Double>()
                        : setDoubleMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, Double> map = (LinkedHashMap<String, Double>) object;
                    return setDoubleMap(map, getMapValue(map, value));
                }
            }
            default -> {return value;}
        }
    }
}
