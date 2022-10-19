package bin.check;

import bin.exception.VariableException;
import bin.orign.variable.list.get.GetList;
import bin.orign.variable.map.get.GetMap;
import bin.orign.variable.set.get.GetSet;
import bin.token.VariableToken;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import static bin.check.VariableCheck.*;

public class VariableTypeCheck implements VariableToken, GetSet, GetList, GetMap {
    public static boolean getVariableTypeCheck(VariableType variableType, String value) {
        return switch (variableType) {
            case Boolean -> isBoolean(value);
            case Character -> isCharacter(value);
            case Double -> isDouble(value);
            case Float -> isFloat(value);
            case Integer -> isInteger(value);
            case Long -> isLong(value);

            case SetBoolean, ListBoolean -> !isListBoolean(value);
            case SetCharacter, ListCharacter -> !isListCharacter(value);
            case SetDouble, ListDouble -> !isListDouble(value);
            case SetFloat, ListFloat -> !isListFloat(value);
            case SetInteger, ListInteger -> !isListInteger(value);
            case SetLong, ListLong -> !isListLong(value);
            case SetString, ListString -> !isListString(value);

            case MapBoolean -> isMapBoolean(value);
            case MapCharacter -> isMapCharacter(value);
            case MapDouble -> isMapDouble(value);
            case MapFloat -> isMapFloat(value);
            case MapInteger -> isMapInteger(value);
            case MapLong -> isMapLong(value);
            case MapString -> isMapString(value);
            case String -> true;
        };
    }

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
            default -> VariableType.String;
        };
    }

    public Object getObject(VariableType variableType, String value, Object object) {
        switch (variableType) {
            case Integer -> {
                if (!isInteger(value)) throw VariableException.typeMatch();
                else return Integer.parseInt(value);
            }
            case Long -> {
                if (!isLong(value)) throw VariableException.typeMatch();
                else return Long.parseLong(value);
            }
            case Boolean -> {
                if (!isBoolean(value)) throw VariableException.typeMatch();
                else return value;
            }
            case Character -> {
                if (!isCharacter(value)) throw VariableException.typeMatch();
                else return value.charAt(0);
            }
            case Float -> {
                if (!isLong(value)) throw VariableException.typeMatch();
                else return Float.parseFloat(value);
            }
            case Double -> {
                if (!isLong(value)) throw VariableException.typeMatch();
                else return Double.parseDouble(value);
            }
            case SetInteger -> {
                LinkedHashSet<Integer> set;
                if (value.isBlank()) set = new LinkedHashSet<>();
                else {
                    if (value.startsWith(SET_ADD)) set = getIntegerSet(value.substring(SET_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashSet<Integer>) object).clear();
                        set = getIntegerSet(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? set : ((LinkedHashSet<Integer>) object).addAll(set);
            }
            case SetLong -> {
                LinkedHashSet<Long> set;
                if (value.isBlank()) set = new LinkedHashSet<>();
                else {
                    if (value.startsWith(SET_ADD)) set = getLongSet(value.substring(SET_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashSet<Long>) object).clear();
                        set = getLongSet(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? set : ((LinkedHashSet<Long>) object).addAll(set);
            }
            case SetBoolean -> {
                LinkedHashSet<String> set;
                if (value.isBlank()) set = new LinkedHashSet<>();
                else {
                    if (value.startsWith(SET_ADD)) set = getBoolSet(value.substring(SET_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashSet<String>) object).clear();
                        set = getBoolSet(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? set : ((LinkedHashSet<String>) object).addAll(set);
            }
            case SetString -> {
                LinkedHashSet<String> set;
                if (value.isBlank()) set = new LinkedHashSet<>();
                else {
                    if (value.startsWith(SET_ADD)) set = getStringSet(value.substring(SET_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashSet<String>) object).clear();
                        set = getStringSet(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? set : ((LinkedHashSet<String>) object).addAll(set);
            }
            case SetCharacter -> {
                LinkedHashSet<Character> set;
                if (value.isBlank()) set = new LinkedHashSet<>();
                else {
                    if (value.startsWith(SET_ADD)) set = getCharacterSet(value.substring(SET_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashSet<Character>) object).clear();
                        set = getCharacterSet(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? set : ((LinkedHashSet<Character>) object).addAll(set);
            }
            case SetFloat -> {
                LinkedHashSet<Float> set;
                if (value.isBlank()) set = new LinkedHashSet<>();
                else {
                    if (value.startsWith(SET_ADD)) set = getFlotSet(value.substring(SET_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashSet<Float>) object).clear();
                        set = getFlotSet(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? set : ((LinkedHashSet<Float>) object).addAll(set);
            }
            case SetDouble -> {
                LinkedHashSet<Double> set;
                if (value.isBlank()) set = new LinkedHashSet<>();
                else {
                    if (value.startsWith(SET_ADD)) set = getDoubleSet(value.substring(SET_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashSet<Double>) object).clear();
                        set = getDoubleSet(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? set : ((LinkedHashSet<Double>) object).addAll(set);
            }

            case ListInteger -> {
                LinkedList<Integer> list;
                if (value.isBlank()) list = new LinkedList<>();
                else {
                    if (value.startsWith(LIST_ADD)) list = getIntegerList(value.substring(LIST_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedList<Integer>) object).clear();
                        list = getIntegerList(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? list : ((LinkedList<Integer>) object).addAll(list);
            }
            case ListLong -> {
                LinkedList<Long> list;
                if (value.isBlank()) list = new LinkedList<>();
                else {
                    if (value.startsWith(LIST_ADD)) list = getLongList(value.substring(LIST_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedList<Long>) object).clear();
                        list = getLongList(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? list : ((LinkedList<Long>) object).addAll(list);
            }
            case ListBoolean -> {
                LinkedList<String> list;
                if (value.isBlank()) list = new LinkedList<>();
                else {
                    if (value.startsWith(LIST_ADD)) list = getBoolList(value.substring(LIST_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedList<String>) object).clear();
                        list = getBoolList(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? list : ((LinkedList<String>) object).addAll(list);
            }
            case ListString -> {
                LinkedList<String> list;
                if (value.isBlank()) list = new LinkedList<>();
                else {
                    if (value.startsWith(LIST_ADD)) list = getStringList(value.substring(LIST_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedList<String>) object).clear();
                        list = getStringList(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? list : ((LinkedList<String>) object).addAll(list);
            }
            case ListCharacter -> {
                LinkedList<Character> list;
                if (value.isBlank()) list = new LinkedList<>();
                else {
                    if (value.startsWith(LIST_ADD)) list = getCharacterList(value.substring(LIST_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedList<Character>) object).clear();
                        list = getCharacterList(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? list : ((LinkedList<Character>) object).addAll(list);
            }
            case ListFloat -> {
                LinkedList<Float> list;
                if (value.isBlank()) list = new LinkedList<>();
                else {
                    if (value.startsWith(LIST_ADD)) list = getFlotList(value.substring(LIST_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedList<Float>) object).clear();
                        list = getFlotList(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? list : ((LinkedList<Float>) object).addAll(list);
            }
            case ListDouble -> {
                LinkedList<Double> list;
                if (value.isBlank()) list = new LinkedList<>();
                else {
                    if (value.startsWith(LIST_ADD)) list = getDoubleList(value.substring(LIST_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedList<Double>) object).clear();
                        list = getDoubleList(value.substring(VARIABLE_PUT.length()));
                    } else throw VariableException.noGrammar();
                }
                return object == null ? list : ((LinkedList<Double>) object).addAll(list);
            }

            case MapInteger -> {
                LinkedHashMap<String, Integer> map;
                if (value.isBlank()) map = new LinkedHashMap<>();
                else {
                    if (value.startsWith(MAP_ADD)) map = getIntegerMap(value.substring(MAP_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashMap<String, Integer>) object).clear();
                        map = getIntegerMap(value.substring(VARIABLE_PUT.length()));
                    }
                    else throw VariableException.noGrammar();
                }
                if (object != null) ((LinkedHashMap<String, Integer>) object).putAll(map);
                return map;
            }
            case MapLong -> {
                LinkedHashMap<String, Long> map;
                if (value.isBlank()) map = new LinkedHashMap<>();
                else {
                    if (value.startsWith(MAP_ADD)) map = getLongMap(value.substring(MAP_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashMap<String, Integer>) object).clear();
                        map = getLongMap(value.substring(VARIABLE_PUT.length()));
                    }
                    else throw VariableException.noGrammar();
                }
                if (object != null) ((LinkedHashMap<String, Long>) object).putAll(map);
                return map;
            }
            case MapBoolean -> {
                LinkedHashMap<String, String> map;
                if (value.isBlank()) map = new LinkedHashMap<>();
                else {
                    if (value.startsWith(MAP_ADD)) map = getBoolMap(value.substring(MAP_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashMap<String, String>) object).clear();
                        map = getBoolMap(value.substring(VARIABLE_PUT.length()));
                    }
                    else throw VariableException.noGrammar();
                }
                if (object != null) ((LinkedHashMap<String, String>) object).putAll(map);
                return map;
            }
            case MapString -> {
                LinkedHashMap<String, String> map;
                if (value.isBlank()) map = new LinkedHashMap<>();
                else {
                    if (value.startsWith(MAP_ADD)) map = getStringMap(value.substring(MAP_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashMap<String, String>) object).clear();
                        map = getStringMap(value.substring(VARIABLE_PUT.length()));
                    }
                    else throw VariableException.noGrammar();
                }
                if (object != null) ((LinkedHashMap<String, String>) object).putAll(map);
                return map;
            }
            case MapCharacter -> {
                LinkedHashMap<String, Character> map;
                if (value.isBlank()) map = new LinkedHashMap<>();
                else {
                    if (value.startsWith(MAP_ADD)) map = getCharacterMap(value.substring(MAP_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashMap<String, Character>) object).clear();
                        map = getCharacterMap(value.substring(VARIABLE_PUT.length()));
                    }
                    else throw VariableException.noGrammar();
                }
                if (object != null) ((LinkedHashMap<String, Character>) object).putAll(map);
                return map;
            }
            case MapFloat -> {
                LinkedHashMap<String, Float> map;
                if (value.isBlank()) map = new LinkedHashMap<>();
                else {
                    if (value.startsWith(MAP_ADD)) map = getFlotMap(value.substring(MAP_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashMap<String, Float>) object).clear();
                        map = getFlotMap(value.substring(VARIABLE_PUT.length()));
                    }
                    else throw VariableException.noGrammar();
                }
                if (object != null) ((LinkedHashMap<String, Float>) object).putAll(map);
                return map;
            }
            case MapDouble -> {
                LinkedHashMap<String, Double> map;
                if (value.isBlank()) map = new LinkedHashMap<>();
                else {
                    if (value.startsWith(MAP_ADD)) map = getDoubleMap(value.substring(MAP_ADD.length()));
                    else if (value.startsWith(VARIABLE_PUT)) {
                        if (object != null) ((LinkedHashMap<String, Double>) object).clear();
                        map = getDoubleMap(value.substring(VARIABLE_PUT.length()));
                    }
                    else throw VariableException.noGrammar();
                }
                if (object != null) ((LinkedHashMap<String, Double>) object).putAll(map);
                return map;
            }
            default -> {
                return value;
            }
        }
    }
}
