package origin.variable.define;

import origin.variable.model.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface VariableCheck {
    //VariableKind 를 반환하는 메소드
    static VariableKind checkVariableKind(String var) {
        char c = var.charAt(0);
        if (c == 'l' || c == 'ㄹ') return VariableKind.LIST;
        else return VariableKind.ORIGIN;
    }

    //VariableType 을 반환하는 메소드
    static VariableType checkVariableType(String var) {
        char c = var.charAt(1);
        if (c == 'ㅈ' || c == 'i') return VariableType.Integer;
        else if (c == 'ㅉ' || c == 'I') return VariableType.Long;
        else if (c == 'ㅂ' || c == 'b') return VariableType.Boolean;
        else if (c == 'ㄱ' || c == 'c') return VariableType.Character;
        else if (c == 'ㅅ' || c == 'f') return VariableType.Float;
        else if (c == 'ㅆ' || c == 'F') return VariableType.Double;
        else return VariableType.String;
    }

    // 변수값을 넣는 과정
    static void setValue(String var, Object value) {
        for (String key : Repository.repository.keySet()) {
            if (Repository.repository.get(key).containsKey(var))
                Repository.repository.get(key).put(var, value);
        }
    }

    static List<Object> getArray(String var) {
        for (String key : Repository.repository.keySet()) {
            if (Repository.repository.get(key).containsKey(var)) {
                return (List<Object>) Repository.repository.get(key).get(var);
            }
        }
        return null;
    }

    // ㅇㅁㅇ, ㅇㄱㅇ, ㅇㅅㅇ 인지 반환
    static String getCheck(String varName) {
        for (String key : Repository.repository.keySet()) {
            if (Repository.repository.get(key).containsKey(varName))
                return key;
        }
        return "  ";
    }

    //변수명 받아오기
    static boolean check(String varName) {
        for (String key : Repository.repository.keySet()) {
            // key = ㅇㅁㅇ, ㄹㅁㄹ, ㄹㅉㄹ, ...
            if (Repository.repository.get(key).containsKey(varName))
                return check(varName, key);
        }
        return false;
    }

    //리스트 타입이 일치하는지 확인
    // variable : 리스트 타입, value : [값1, 값2, 값3, ...]
    static boolean checkList(VariableType variableType, String value) {
        value = value.trim();
        if (value.startsWith("[") && value.endsWith("]")) {
            String[] values = value.substring(1, value.length()-1).split(",");
            if (values.length == 0) return true;
            return Arrays.stream(values)
                    .allMatch(v -> check(v.trim(), variableType));
        } else return check(value, variableType);
    }

    //해당 저장소에 값이 존재하는지 확인
    static boolean check(String varName, Map<String, Map<String, Object>> repository) {
        for (String key : repository.keySet()) {
            if (repository.get(key).containsKey(varName)) {
                return check(varName, key);
            }
        }
        return false;
    }

    //line : 값 받아오기
    //var : 값 ㅇㅁㅇ, ㅇㅈㅇ 등
    static boolean check(String line, String var) {
        //리스트인지 확인함
        VariableType variableType = checkVariableType(var);
        if (checkVariableKind(var).equals(VariableKind.LIST))
            return checkList(variableType, line);
        else return check(line, variableType);
    }

    static boolean check(String line, VariableType varType) {
        if (varType.equals(VariableType.Boolean)) return isBoolean(line);
        else if (varType.equals(VariableType.Character)) return isCharacter(line);
        else if (varType.equals(VariableType.Double)) return isDouble(line);
        else if (varType.equals(VariableType.Float)) return isFloat(line);
        else if (varType.equals(VariableType.Integer)) return isInteger(line);
        else if (varType.equals(VariableType.Long)) return isLong(line);
        else return varType.equals(VariableType.String);
    }

    static boolean isBoolean(String line) {
        line = line.strip();
        line = line.replace("ㅇㅇ", "true");
        line = line.replace("ㄴㄴ", "false");
        if (line.isBlank()) return false;
        return line.equals("false") || line.equals("true");
    }

    static boolean isCharacter(String line) {
        try {
            return line.length() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDouble(String line) {
        try {
            Double.parseDouble(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isFloat(String line) {
        try {
            Float.parseFloat(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isInteger(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isLong(String line) {
        try {
            Long.parseLong(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
