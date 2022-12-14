package bin.check;

import bin.exception.VariableException;
import bin.token.Token;
import bin.token.cal.BoolToken;

import java.util.regex.Pattern;

import static bin.check.VariableCheck.isInteger;
import static bin.token.cal.BoolToken.*;

public interface VariableCheck {
    // 기본 변수
    static boolean isBoolean(String line) {
        line = line.strip();
        if (line.isBlank()) return false;
        return line.equals(FALSE) || line.equals(TRUE);
    }
    static boolean isCharacter(String line) {
        try {return line.length() == 1 || isInteger(line);}
        catch (Exception e) {return false;}
    }
    static boolean isInteger(String line) {
        try {Integer.parseInt(line);return true;}
        catch (Exception e) {return line.length() == 1;}
    }

    static boolean isDouble(String line) {try {Double.parseDouble(line);return true;} catch (Exception e) {return false;}}
    static boolean isFloat(String line) {try {Float.parseFloat(line);return true;} catch (Exception e) {return false;}}
    static boolean isLong(String line) {try {Long.parseLong(line);return true;} catch (Exception e) {return false;}}

    static String getBoolean(String line) {if (line.equals(FALSE) || line.equals(TRUE)) return line;else throw new VariableException().typeMatch();}
    static double getDouble(String line) {try {return Double.parseDouble(line);} catch (Exception e) {throw new VariableException().typeMatch();}}
    static float getFloat(String line) {try {return Float.parseFloat(line);} catch (Exception e) {throw new VariableException().typeMatch();}}
    static long getLong(String line) {try {return Long.parseLong(line);} catch (Exception e) {throw new VariableException().typeMatch();}}
    static char getCharacter(String line) {
        if (line.length() == 1) return line.charAt(0);
        else {
            try {
                return (char) Integer.parseInt(line);
            } catch (Exception e) {
                throw new VariableException().typeMatch();
            }
        }
    }

    static int getInteger(String line) {
        try {
            return Integer.parseInt(line);
        } catch (Exception e) {
            if (line.length() == 1) return line.charAt(0);
            else throw new VariableException().typeMatch();
        }
    }

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
                .map(String::strip)
                .allMatch(VariableCheck::isBoolean);
    }

    static boolean isListCharacter(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .allMatch(VariableCheck::isCharacter);
    }

    static boolean isListDouble(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .allMatch(VariableCheck::isDouble);
    }

    static boolean isListFloat(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .allMatch(VariableCheck::isFloat);
    }

    static boolean isListInteger(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .allMatch(VariableCheck::isInteger);
    }

    static boolean isListLong(String line) {
        if (!(line.startsWith("[") && line.endsWith("]"))) return true;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return false;
        return !Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
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
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .map(v -> v.split("=", 2))
                .anyMatch(v -> v.length == 2 && isBoolean(v[1]));
    }

    static boolean isMapCharacter(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .map(v -> v.split("=", 2))
                .anyMatch(v -> v.length == 2 && isCharacter(v[1]));
    }

    static boolean isMapDouble(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .map(v -> v.split("=", 2))
                .anyMatch(v -> v.length == 2 && isDouble(v[1]));
    }

    static boolean isMapFloat(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .map(v -> v.split("=", 2))
                .anyMatch(v -> v.length == 2 && isFloat(v[1]));
    }

    static boolean isMapInteger(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .map(v -> v.split("=", 2))
                .anyMatch(v -> v.length == 2 && isInteger(v[1]));
    }

    static boolean isMapLong(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .map(v -> v.split("=", 2))
                .anyMatch(v -> v.length == 2 && isLong(v[1]));
    }

    static boolean isMapString(String line) {
        if (!(line.startsWith("{") && line.endsWith("}"))) return false;
        line = line.substring(1, line.length()-1);
        if (line.isBlank()) return true;
        return Pattern.compile(Token.COMMA)
                .splitAsStream(line)
                .map(String::strip)
                .map(v -> v.split("=", 2))
                .anyMatch(v -> v.length == 2);
    }
}
