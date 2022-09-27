package bin.orign.variable.list.get;

import bin.exception.VariableException;
import bin.token.Token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bin.check.VariableCheck.*;
import static bin.check.VariableCheck.isListString;

public interface GetList {
    default LinkedList<String> getBoolList(String line) {
        if (listCheck(line) && isBoolean(line)) return new LinkedList<>(){{ add(line); }};
        if (isListBoolean(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    default LinkedList<Character> getCharacterList(String line) {
        if (listCheck(line) && isCharacter(line)) return new LinkedList<>() {{add(line.charAt(0));}};
        if (isListCharacter(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(v -> v.charAt(0))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    default LinkedList<Double> getDoubleList(String line) {
        if (listCheck(line) && isDouble(line)) return new LinkedList<>() {{ add(Double.parseDouble(line)); }};
        if (isListDouble(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(Double::parseDouble)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    default LinkedList<Float> getFlotList(String line) {
        if (listCheck(line) && isFloat(line)) return new LinkedList<>() {{ add(Float.parseFloat(line)); }};
        if (isListFloat(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(Float::parseFloat)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    default LinkedList<Integer> getIntegerList(String line) {
        if (listCheck(line) && isInteger(line)) return new LinkedList<>() {{ add(Integer.parseInt(line)); }};
        if (isListInteger(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    default LinkedList<Long> getLongList(String line) {
        if (listCheck(line) && isLong(line)) return new LinkedList<>() {{ add(Long.parseLong(line)); }};
        if (isListLong(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length() - 1))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    default LinkedList<String> getStringList(String line) {
        if (listCheck(line)) return new LinkedList<>() {{ add(line); }};
        if (!isListString(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length() - 1))
                .map(String::trim)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
