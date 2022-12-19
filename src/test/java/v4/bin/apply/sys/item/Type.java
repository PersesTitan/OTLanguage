package v4.bin.apply.sys.item;

import bin.exception.VariableException;
import bin.token.MergeToken;
import bin.token.VariableToken;
import com.google.common.math.DoubleMath;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import v4.calculator.tool.Calculator;

import java.util.*;

import static bin.check.VariableCheck.*;

@RequiredArgsConstructor(access = AccessLevel.MODULE)
public enum Type implements MergeToken, Calculator {
    INT_VARIABLE(VariableToken.INT_VARIABLE),
    LONG_VARIABLE(VariableToken.LONG_VARIABLE),
    BOOL_VARIABLE(VariableToken.BOOL_VARIABLE),
    STRING_VARIABLE(VariableToken.STRING_VARIABLE),
    CHARACTER_VARIABLE(VariableToken.CHARACTER_VARIABLE),
    FLOAT_VARIABLE(VariableToken.FLOAT_VARIABLE),
    DOUBLE_VARIABLE(VariableToken.DOUBLE_VARIABLE),
    SET_INTEGER(VariableToken.SET_INTEGER),
    SET_LONG(VariableToken.SET_LONG),
    SET_BOOLEAN(VariableToken.SET_BOOLEAN),
    SET_STRING(VariableToken.SET_STRING),
    SET_CHARACTER(VariableToken.SET_CHARACTER),
    SET_FLOAT(VariableToken.SET_FLOAT),
    SET_DOUBLE(VariableToken.SET_DOUBLE),
    LIST_INTEGER(VariableToken.LIST_INTEGER),
    LIST_LONG(VariableToken.LIST_LONG),
    LIST_BOOLEAN(VariableToken.LIST_BOOLEAN),
    LIST_STRING(VariableToken.LIST_STRING),
    LIST_CHARACTER(VariableToken.LIST_CHARACTER),
    LIST_FLOAT(VariableToken.LIST_FLOAT),
    LIST_DOUBLE(VariableToken.LIST_DOUBLE),
    MAP_INTEGER(VariableToken.MAP_INTEGER),
    MAP_LONG(VariableToken.MAP_LONG),
    MAP_BOOLEAN(VariableToken.MAP_BOOLEAN),
    MAP_STRING(VariableToken.MAP_STRING),
    MAP_CHARACTER(VariableToken.MAP_CHARACTER),
    MAP_FLOAT(VariableToken.MAP_FLOAT),
    MAP_DOUBLE(VariableToken.MAP_DOUBLE),
    ETC(null);

    @Getter
    private final String type;

    // value => Object value
    // type : ㅇㅁㅇ
    public Object getValue(String type, String value, LinkedList<TypeMap> repositoryArray) {
        return switch (this) {
            case INT_VARIABLE -> {
                try {
                    yield getInteger(value);
                } catch (VariableException e) {
                    double number = getNumber(value, repositoryArray);
                    if (DoubleMath.isMathematicalInteger(number)) yield (int) number;
                    else throw new VariableException().typeMatch();
                }
            }
            case LONG_VARIABLE -> {
                double number = getNumber(value, repositoryArray);
                if (DoubleMath.isMathematicalInteger(number)) yield (long) number;
                else throw new VariableException().typeMatch();
            }
            case BOOL_VARIABLE -> Calculator.getBool(value, repositoryArray);
            case STRING_VARIABLE -> value;
            case CHARACTER_VARIABLE -> getCharacter(value);
            case FLOAT_VARIABLE -> (float) getNumber(value, repositoryArray);
            case DOUBLE_VARIABLE -> getNumber(value, repositoryArray);
            case SET_INTEGER, SET_LONG, SET_BOOLEAN, SET_STRING, SET_CHARACTER, SET_FLOAT, SET_DOUBLE,
                LIST_INTEGER, LIST_LONG, LIST_BOOLEAN, LIST_STRING, LIST_CHARACTER, LIST_FLOAT, LIST_DOUBLE,
                MAP_INTEGER, MAP_LONG, MAP_BOOLEAN, MAP_STRING, MAP_CHARACTER, MAP_FLOAT, MAP_DOUBLE
                    -> getObject(this.getType(), value, repositoryArray);
            case ETC -> getObject(type, value, repositoryArray);
        };
    }

    private Object getObject(String type, String variableName, LinkedList<TypeMap> repositoryArray) {
        int count = accessCount(variableName, repositoryArray.size());
        if (count == -1) throw new VariableException().localNoVariable();
        variableName = variableName.substring(count);
        // 존재하는지 확인하는 확인하는 로직
        if (repositoryArray.get(count).checkVariable(variableName)) {
            return repositoryArray.get(count).getCheckValue(type, variableName);
        } else throw new VariableException().localNoVariable();
    }

    private static List<String> types = new ArrayList<>();
    public static <V> V casting(Class<V> klass, Object value) {
        try {
            return klass.cast(value);
        } catch (ClassCastException e) {
            throw new VariableException().typeMatch();
        }
    }

    // ex) type : ㅇㅁㅇ => Type.STRING_VARIABLE
    public static Type getType(String type) {
        for (Type t : values()) {
            if (Objects.equals(type, t.getType())) return t;
        }
        return ETC;
    }

    // 허용된 타입인지 확인하는 로직
    // 허용 : true, 비허용 : false
    public static boolean isType(String type) {
        for (Type t : values()) {
            if (Objects.equals(type, t.getType())) return true;
        }
        return types.contains(type);
    }
}
