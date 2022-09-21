package bin.orign.variable.set.get;

import bin.exception.VariableException;
import bin.token.Token;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bin.check.VariableCheck.*;

public interface GetSet {
    default LinkedHashSet<String> getBoolSet(String line) {
        if (listCheck(line) && isBoolean(line)) return new LinkedHashSet<>() {{ add(line); }};
        if (isListBoolean(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    default LinkedHashSet<Character> getCharacterSet(String line) {
        if (listCheck(line) && isCharacter(line)) return new LinkedHashSet<>() {{add(line.charAt(0));}};
        if (isListCharacter(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(v -> v.charAt(0))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    default LinkedHashSet<Double> getDoubleSet(String line) {
        if (listCheck(line) && isDouble(line)) return new LinkedHashSet<>() {{ add(Double.parseDouble(line)); }};
        if (isListDouble(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(Double::parseDouble)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    default LinkedHashSet<Float> getFlotSet(String line) {
        if (listCheck(line) && isFloat(line)) return new LinkedHashSet<>() {{ add(Float.parseFloat(line)); }};
        if (isListFloat(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(Float::parseFloat)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    default LinkedHashSet<Integer> getIntegerSet(String line) {
        if (listCheck(line) && isInteger(line)) return new LinkedHashSet<>() {{ add(Integer.parseInt(line)); }};
        if (isListInteger(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length()-1))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    default LinkedHashSet<Long> getLongSet(String line) {
        if (listCheck(line) && isLong(line)) return new LinkedHashSet<>() {{ add(Long.parseLong(line)); }};
        if (isListLong(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length() - 1))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    default LinkedHashSet<String> getStringSet(String line) {
        if (listCheck(line)) return new LinkedHashSet<>() {{ add(line); }};
        if (!isListString(line)) throw VariableException.typeMatch();
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line.substring(1, line.length() - 1))
                .map(String::trim)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
