package bin.check;

import bin.token.Token;
import bin.token.cal.BoolToken;

import java.util.regex.Pattern;

public interface VariableCheck {
    // 기본 변수
    static boolean isBoolean(String line) {
        line = line.strip();
        if (line.isBlank()) return false;
        return line.equals(BoolToken.FALSE) || line.equals(BoolToken.TRUE);
    }
    static boolean isCharacter(String line) {try {return line.length() == 1;} catch (Exception e) {return false;}}
    static boolean isDouble(String line) {try {Double.parseDouble(line);return true;} catch (Exception e) {return false;}}
    static boolean isFloat(String line) {try {Float.parseFloat(line);return true;} catch (Exception e) {return false;}}
    static boolean isInteger(String line) {try {Integer.parseInt(line);return true;} catch (Exception e) {return false;}}
    static boolean isLong(String line) {try {Long.parseLong(line);return true;} catch (Exception e) {return false;}}

    static boolean listCheck(String line) {
        return !line.startsWith("[") || !line.endsWith("]");
    }

    // 리스트 변수
    static boolean isListBoolean(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::trim)
                .allMatch(VariableCheck::isBoolean);
    }

    static boolean isListCharacter(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::trim)
                .allMatch(VariableCheck::isCharacter);
    }

    static boolean isListDouble(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::trim)
                .allMatch(VariableCheck::isDouble);
    }

    static boolean isListFloat(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::trim)
                .allMatch(VariableCheck::isFloat);
    }

    static boolean isListInteger(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::trim)
                .allMatch(VariableCheck::isInteger);
    }

    static boolean isListLong(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::trim)
                .allMatch(VariableCheck::isLong);
    }

    static boolean isListString(String line) {
        return (line.startsWith("[") && line.endsWith("]"));
    }

    // Map 변수
    static boolean isMapBoolean(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA).splitAsStream(line)
                .map(String::trim)
                .map(v -> v.split(Token.MAP_EQUAL, 2))
                .anyMatch(v -> v.length == 2 && isBoolean(v[1]));
    }

    static boolean isMapCharacter(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA).splitAsStream(line)
                .map(String::trim)
                .map(v -> v.split(Token.MAP_EQUAL, 2))
                .anyMatch(v -> v.length == 2 && isCharacter(v[1]));
    }

    static boolean isMapDouble(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA).splitAsStream(line)
                .map(String::trim)
                .map(v -> v.split(Token.MAP_EQUAL, 2))
                .anyMatch(v -> v.length == 2 && isDouble(v[1]));
    }

    static boolean isMapFloat(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA).splitAsStream(line)
                .map(String::trim)
                .map(v -> v.split(Token.MAP_EQUAL, 2))
                .anyMatch(v -> v.length == 2 && isFloat(v[1]));
    }

    static boolean isMapInteger(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA).splitAsStream(line)
                .map(String::trim)
                .map(v -> v.split(Token.MAP_EQUAL, 2))
                .anyMatch(v -> v.length == 2 && isInteger(v[1]));
    }

    static boolean isMapLong(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA).splitAsStream(line)
                .map(String::trim)
                .map(v -> v.split(Token.MAP_EQUAL, 2))
                .anyMatch(v -> v.length == 2 && isLong(v[1]));
    }

    static boolean isMapString(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA).splitAsStream(line)
                .map(String::trim)
                .map(v -> v.split(Token.MAP_EQUAL, 2))
                .anyMatch(v -> v.length == 2);
    }
}
