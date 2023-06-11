package bin.token.check;

import bin.apply.Repository;
import bin.apply.mode.TypeMode;
import bin.apply.repository.create.CreateMap;
import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.parser.param.ParamToken;
import bin.token.KlassToken;
import bin.token.Token;

import java.util.Objects;

public interface CheckToken {
    static boolean isKlass(String type) {
        return Repository.createWorks == null
                || (!KlassToken.RESERVE.contains(type) && Repository.createWorks.containsKey(type));
    }

    static boolean startWith(String line, char s) {
        return !line.isEmpty() && line.getBytes()[0] == s;
    }

    static boolean endWith(String line, char e) {
        byte[] ta = line.getBytes();
        return ta.length > 0 && ta[ta.length-1] == e;
    }

    static boolean bothCheck(String line, char s, char e) {
        byte[] ta = line.getBytes();
        return ta.length > 0 && ta[0] == s && ta[ta.length-1] == e;
    }

    static boolean isParams(String line) {
        return bothCheck(line, Token.PARAM_S, Token.PARAM_E);
    }

    static boolean isListType(String type) {
        return KlassToken.LISTS.contains(type);
    }

    static boolean isOriginType(String type) {
        return KlassToken.ORIGINS.contains(type);
    }

    static boolean isSet(String line) {
        return bothCheck(line, Token.SET_S, Token.SET_E);
    }

    static boolean isList(String line) {
        return bothCheck(line, Token.LIST_S, Token.LIST_E);
    }

    static boolean isMap(String line) {
        return bothCheck(line, Token.LIST_S, Token.LIST_E);
    }

    static boolean isBlankType(String type) {
        return switch (type) {
            case KlassToken.STRING_VARIABLE, KlassToken.CHARACTER_VARIABLE -> true;
            default -> false;
        };
    }

    static boolean isName(String name) {
        return CheckName.isName(name);
    }

    static boolean isUseName(String name) {
        return CheckName.isUseName(name);
    }

    static TypeMode checkType(String line) {
        return CheckType.checkType(line);
    }

    static boolean isBlank(char c) {
        return Token.BLANKS.contains(c);
    }

    static void checkVariableName(String name) {
        if (!CheckToken.isName(name)) throw VariableException.NO_DEFINE_NAME.getThrow(name);
        if (CheckToken.isKlass(name)) throw VariableException.KLASS_NAME.getThrow(name);
        if (Token.RESERVE.contains(name)) throw VariableException.RESERVED_USE.getThrow(name);
//        if (Repository.methodWorks.hasStatic(name)) throw VariableException.STATIC_NAME_ERROR.getThrow(name);
    }

    static void checkParamLength(int a, int b) {
        if (a != b) throw MatchException.PARAM_COUNT_ERROR.getThrow(a + " != " + b);
    }

    static String getType(ParamToken[] tokens) {
        return CheckType.getType(tokens);
    }
}
