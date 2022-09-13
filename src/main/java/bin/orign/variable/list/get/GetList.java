package bin.orign.variable.list.get;

import bin.exception.VariableException;
import bin.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bin.check.VariableCheck.*;
import static bin.check.VariableCheck.isListString;

public interface GetList {
    default List<String> getBoolList(String line) {
        if (!listCheck(line) && isBoolean(line)) return new ArrayList<>(){{ add(line); }};
        if (!isListBoolean(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    default List<Character> getCharacterList(String line) {
        if (!listCheck(line) && isCharacter(line)) return new ArrayList<>() {{add(line.charAt(0));}};
        if (!isListCharacter(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(v -> v.charAt(0))
                .collect(Collectors.toList());
    }

    default List<Double> getDoubleList(String line) {
        if (!listCheck(line) && isDouble(line)) return new ArrayList<>() {{ add(Double.parseDouble(line)); }};
        if (!isListDouble(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    default List<Float> getFlotList(String line) {
        if (!listCheck(line) && isFloat(line)) return new ArrayList<>() {{ add(Float.parseFloat(line)); }};
        if (!isListFloat(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(Float::parseFloat)
                .collect(Collectors.toList());
    }

    default List<Integer> getIntegerList(String line) {
        if (!listCheck(line) && isInteger(line)) return new ArrayList<>() {{ add(Integer.parseInt(line)); }};
        if (!isListInteger(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    default List<Long> getLongList(String line) {
        if (!listCheck(line) && isLong(line)) return new ArrayList<>() {{ add(Long.parseLong(line)); }};
        if (!isListLong(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length() - 1))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    default List<String> getStringList(String line) {
        if (!listCheck(line)) return new ArrayList<>() {{ add(line); }};
        if (!isListString(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length() - 1))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
