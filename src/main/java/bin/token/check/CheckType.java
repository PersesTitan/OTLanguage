package bin.token.check;

import bin.apply.calculator.bool.CalculatorBool;
import bin.apply.calculator.bool.CalculatorCompare;
import bin.apply.mode.TypeMode;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.KlassToken;
import bin.token.Token;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

interface CheckType {
    private static boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isNumbers(char[] cs, int s, int e) {
        for (int i = s; i<e; i++) if (!isNumber(cs[i])) return false;
        return true;
    }

    private static TypeMode isFloat(char[] cs, int position) {
        final int size = cs.length - 1;
        if (size == position) return TypeMode.STRING;
        int count = 0;
        for (int i = size; i > position; i--) {
            if (count == 0 && cs[i] == '0') continue;
            if (isNumber(cs[i])) count++;
            else return TypeMode.STRING;
        }
        if (isNumbers(cs, 1, position)) return count > 8 ? TypeMode.DOUBLE : TypeMode.FLOAT;
        else return TypeMode.STRING;
    }

    private static TypeMode isInteger(char[] cs, boolean isSing) {
        final int size = cs.length;
        int count = 0;
        for (int i = isSing ? 1 : 0; i < size; i++) {
            if (count == 0 && cs[i] == '0') continue;
            if (isNumber(cs[i])) count++;
            else return TypeMode.STRING;
        }
        return count > 10 ? TypeMode.LONG : TypeMode.INTEGER;
    }

    static TypeMode checkType(String line) {
        if (CalculatorBool.check(line) || CalculatorCompare.check(line)) return TypeMode.BOOLEAN;
        int size = line.length();
        char[] cs = line.toCharArray();
        return switch (size) {
            case 0 -> TypeMode.STRING;
            case 1 -> isNumber(cs[0]) ? TypeMode.INTEGER : TypeMode.CHARACTER;
            case 2 -> {
                if (line.equals(Token.TRUE) || line.equals(Token.FALSE)) yield TypeMode.BOOLEAN;
                if (isNumber(cs[1]) && (cs[0] == '+' || cs[0] == '-' || isNumber(cs[0]))) yield TypeMode.INTEGER;
                else yield TypeMode.STRING;
            }
            default -> {
                if (cs[0] == '+' || cs[0] == '-') {
                    int position = line.indexOf('.');
                    if (position == -1) yield isInteger(cs, true);
                    else if (position == 1) yield TypeMode.STRING;
                    else if (position == line.lastIndexOf('.')) yield isFloat(cs, position);
                    else yield TypeMode.STRING;
                } else if (isNumber(cs[0])) {
                    int position = line.indexOf('.');
                    if (position == -1) yield isInteger(cs, false);
                    else if (position == line.lastIndexOf('.')) yield isFloat(cs, position);
                    else yield TypeMode.STRING;
                } else yield TypeMode.STRING;
            }
        };
    }

    static TypeMode getTypeMode(ParamToken[] tokens) {
        Set<TypeMode> types = Arrays.stream(tokens)
                .map(ParamToken::getReplace)
                .map(TypeMode::get)
                .collect(Collectors.toSet());
        if (types.size() == 1) return types.stream().findFirst().get();
        else if (types.contains(TypeMode.STRING)) return TypeMode.STRING;
        else if (types.contains(TypeMode.BOOLEAN)) return TypeMode.STRING;
        else if (types.contains(TypeMode.CHARACTER)) return TypeMode.STRING;
        else return TypeMode.STRING;
//        else if (types.contains())
    }

    static String getType(ParamToken[] tokens) {
        Set<String> types = Arrays.stream(tokens)
                .map(ParamToken::getReplace)
                .collect(Collectors.toSet());
        if (types.contains(null)) throw VariableException.TYPE_ERROR.getThrow(null);
        else if (types.size() == 1) return types.stream().findFirst().get();
        else if (types.stream().allMatch(CheckToken::isOriginType)) {
            if (types.contains(KlassToken.STRING_VARIABLE)
                    || types.contains(KlassToken.BOOL_VARIABLE)
                    || types.contains(KlassToken.CHARACTER_VARIABLE)) return KlassToken.STRING_VARIABLE;
            else if (types.contains(KlassToken.DOUBLE_VARIABLE)) return KlassToken.DOUBLE_VARIABLE;
            else if (types.contains(KlassToken.FLOAT_VARIABLE)) return KlassToken.FLOAT_VARIABLE;
            else if (types.contains(KlassToken.LONG_VARIABLE)) return KlassToken.LONG_VARIABLE;
            else return KlassToken.STRING_VARIABLE;
        } else throw VariableException.TYPE_ERROR.getThrow(types);
    }
}
