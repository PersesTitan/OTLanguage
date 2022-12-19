package bin.check;

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
    private static VariableTypeCheck variableTypeCheck;
    private VariableTypeCheck() {}
    public static VariableTypeCheck getInstance() {
        if (variableTypeCheck == null) {
            synchronized (VariableTypeCheck.class) {
                variableTypeCheck = new VariableTypeCheck();
            }
        }
        return variableTypeCheck;
    }

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
        return switch (variableType) {
            case Method -> {
                if (values instanceof MethodItemReturn || values instanceof MethodItemVoid) yield values;
                else throw new VariableException().methodTypeMatch();
            }
            case Integer -> getInteger(value);
            case Long -> getLong(value);
            case Boolean -> getBoolean(value);
            case Character -> getCharacter(value);
            case Float -> getFloat(value);
            case Double -> getDouble(value);
            case SetInteger -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashSet<Integer>()
                        : setIntegerSet(new LinkedHashSet<>(), getValueSet(value));
                else if (object instanceof LinkedHashSet<?>) {
                    yield setIntegerSet((LinkedHashSet<Integer>) object, getValueSet(value));
                } else {
                    LinkedHashSet<Integer> set = (LinkedHashSet<Integer>) object;
                    yield setIntegerSet(set, getSetValue(set, value));
                }
            }
            case SetLong -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashSet<Long>()
                        : setLongSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<Long> set = (LinkedHashSet<Long>) object;
                    yield setLongSet(set, getSetValue(set, value));
                }
            }
            case SetBoolean -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashSet<String>()
                        : setBoolSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<String> set = (LinkedHashSet<String>) object;
                    yield setBoolSet(set, getSetValue(set, value));
                }
            }
            case SetString -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashSet<String>()
                        : setStringSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<String> set = (LinkedHashSet<String>) object;
                    yield setStringSet(set, getSetValue(set, value));
                }
            }
            case SetCharacter -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashSet<Character>()
                        : setCharacterSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<Character> set = (LinkedHashSet<Character>) object;
                    yield setCharacterSet(set, getSetValue(set, value));
                }
            }
            case SetFloat -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashSet<Float>()
                        : setFlotSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<Float> set = (LinkedHashSet<Float>) object;
                    yield setFlotSet(set, getSetValue(set, value));
                }
            }
            case SetDouble -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashSet<Double>()
                        : setDoubleSet(new LinkedHashSet<>(), getValueSet(value));
                else {
                    LinkedHashSet<Double> set = (LinkedHashSet<Double>) object;
                    yield setDoubleSet(set, getSetValue(set, value));
                }
            }
            case ListInteger -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedList<Integer>()
                        : setIntegerList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<Integer> list = (LinkedList<Integer>) object;
                    yield setIntegerList(list, getListValue(list, value));
                }
            }
            case ListLong -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedList<Long>()
                        : setLongList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<Long> list = (LinkedList<Long>) object;
                    yield setLongList(list, getListValue(list, value));
                }
            }
            case ListBoolean -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedList<String>()
                        : setBoolList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<String> list = (LinkedList<String>) object;
                    yield setBoolList(list, getListValue(list, value));
                }
            }
            case ListString -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedList<String>()
                        : setStringList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<String> list = (LinkedList<String>) object;
                    yield setStringList(list, getListValue(list, value));
                }
            }
            case ListCharacter -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedList<Character>()
                        : setCharacterList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<Character> list = (LinkedList<Character>) object;
                    yield setCharacterList(list, getListValue(list, value));
                }
            }
            case ListFloat -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedList<Float>()
                        : setFlotList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<Float> list = (LinkedList<Float>) object;
                    yield setFlotList(list, getListValue(list, value));
                }
            }
            case ListDouble -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedList<Double>()
                        : setDoubleList(new LinkedList<>(), getValueList(value));
                else {
                    LinkedList<Double> list = (LinkedList<Double>) object;
                    yield setDoubleList(list, getListValue(list, value));
                }
            }
            case MapInteger -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashMap<String, Integer>()
                        : setIntegerMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, Integer> map = (LinkedHashMap<String, Integer>) object;
                    yield setIntegerMap(map, getMapValue(map, value));
                }
            }
            case MapLong -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashMap<String, Long>()
                        : setLongMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, Long> map = (LinkedHashMap<String, Long>) object;
                    yield setLongMap(map, getMapValue(map, value));
                }
            }
            case MapBoolean -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashMap<String, String>()
                        : setBoolMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) object;
                    yield setBoolMap(map, getMapValue(map, value));
                }
            }
            case MapString -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashMap<String, String>()
                        : setStringMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) object;
                    yield setStringMap(map, getMapValue(map, value));
                }
            }
            case MapCharacter -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashMap<String, Character>()
                        : setCharacterMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, Character> map = (LinkedHashMap<String, Character>) object;
                    yield setCharacterMap(map, getMapValue(map, value));
                }
            }
            case MapFloat -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashMap<String, Float>()
                        : setFlotMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, Float> map = (LinkedHashMap<String, Float>) object;
                    yield setFlotMap(map, getMapValue(map, value));
                }
            }
            case MapDouble -> {
                if (object == null) yield value.isBlank()
                        ? new LinkedHashMap<String, Double>()
                        : setDoubleMap(new LinkedHashMap<>(), getValueMap(value));
                else {
                    LinkedHashMap<String, Double> map = (LinkedHashMap<String, Double>) object;
                    yield setDoubleMap(map, getMapValue(map, value));
                }
            }
            case String -> value;
        };
    }
}
