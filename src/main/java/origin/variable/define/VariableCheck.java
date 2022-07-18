package origin.variable.define;

public class VariableCheck {
    private final static VariableCheck variableCheck = new VariableCheck();

    //line : 값 받아오기, var : 값 ㅇㅁㅇ, ㅇㅈㅇ 등
    public static boolean check(String line, String var) {
        char c = var.charAt(1);
        if (c == 'ㅈ') return check(line, VariableType.Integer);
        else if (c == 'ㅉ') return check(line, VariableType.Long);
        else if (c == 'ㅂ') return check(line, VariableType.Boolean);
        else if (c == 'ㄱ') return check(line, VariableType.Character);
        else if (c == 'ㅅ') return check(line, VariableType.Float);
        else if (c == 'ㅆ') return check(line, VariableType.Double);
        else return check(line, VariableType.String);
    }

    public static boolean check(String line, VariableType varType) {
        if (varType.equals(VariableType.Boolean)) return variableCheck.isBoolean(line);
        else if (varType.equals(VariableType.Character)) return variableCheck.isCharacter(line);
        else if (varType.equals(VariableType.Double)) return variableCheck.isDouble(line);
        else if (varType.equals(VariableType.Float)) return variableCheck.isFloat(line);
        else if (varType.equals(VariableType.Integer)) return variableCheck.isInteger(line);
        else if (varType.equals(VariableType.Long)) return variableCheck.isLong(line);
        else return varType.equals(VariableType.String);
    }

    private boolean isBoolean(String line) {
        line = line.strip();
        line = line.replace("ㅇㅇ", "true");
        line = line.replace("ㄴㄴ", "false");
        if (line.isBlank()) return false;
        return line.equals("false") || line.equals("true");
    }

    private boolean isCharacter(String line) {
        try {
            return line.length() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isDouble(String line) {
        try {
            Double.parseDouble(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isFloat(String line) {
        try {
            Float.parseFloat(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isInteger(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isLong(String line) {
        try {
            Long.parseLong(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
