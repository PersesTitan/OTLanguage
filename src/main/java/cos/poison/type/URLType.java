package cos.poison.type;

import bin.exception.VariableException;
import bin.token.KlassToken;
import bin.token.Token;
import bin.token.check.CheckToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public enum URLType {
    INT     ("[+\\-]?\\\\d+",
            KlassToken.INT_VARIABLE,
            Integer::parseInt),
    FLOAT   (INT.text + "\\\\.\\\\d+",
            KlassToken.FLOAT_VARIABLE,
            Float::parseFloat),
    LONG    (INT.text,
            KlassToken.LONG_VARIABLE,
            Long::parseLong),
    DOUBLE  (FLOAT.text,
            KlassToken.DOUBLE_VARIABLE,
            Double::parseDouble),
    CHAR    (".",
            KlassToken.CHARACTER_VARIABLE,
            v -> v.charAt(0)),
    STRING  (CHAR.text + "*",
            KlassToken.STRING_VARIABLE,
            String::toString),
    BOOL    ("(true|false|ㅇㅇ|ㄴㄴ)",
            KlassToken.BOOL_VARIABLE,
            v -> v.equals(Token.TRUE) || v.equals("true"));

    private final String text;
    public final String type;
    private final Function<String, Object> function;

    public static URLType getType(String type) {
        for (URLType item : URLType.values()) if (item.type.equals(type)) return item;
        throw VariableException.TYPE_ERROR.getThrow(type);
    }

    public Object value(String value) {
        try {
            return function.apply(value);
        } catch (NumberFormatException e) {
            throw VariableException.VALUE_ERROR.getThrow(value);
        }
    }

    public static String getText(String type) {
        return (switch (type) {
            case KlassToken.INT_VARIABLE -> INT;
            case KlassToken.LONG_VARIABLE -> LONG;
            case KlassToken.FLOAT_VARIABLE -> FLOAT;
            case KlassToken.DOUBLE_VARIABLE -> DOUBLE;
            case KlassToken.CHARACTER_VARIABLE -> CHAR;
            case KlassToken.STRING_VARIABLE -> STRING;
            case KlassToken.BOOL_VARIABLE -> BOOL;
            default -> {
                if (CheckToken.isKlass(type)) throw VariableException.TYPE_ERROR.getThrow(type);
                else throw VariableException.NO_DEFINE_TYPE.getThrow(type);
            }
        }).text;
    }
}
