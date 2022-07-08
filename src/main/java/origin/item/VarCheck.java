package origin.item;

import origin.item.kind.VarType;
import org.jetbrains.annotations.NotNull;

public class VarCheck {
    /**
     * @param line 값을 받아옴
     * @param varType 변수 타입이 무엇인지 확인함
     * @return 맞는 변수 타입을 반환함
     */
    public boolean check(@NotNull String line, @NotNull VarType varType) {
        if (varType.equals(VarType.Boolean)) return isBoolean(line);
        else if (varType.equals(VarType.Character)) return isCharacter(line);
        else if (varType.equals(VarType.Double)) return isDouble(line);
        else if (varType.equals(VarType.Float)) return isFloat(line);
        else if (varType.equals(VarType.Integer)) return isInteger(line);
        else if (varType.equals(VarType.Long)) return isLong(line);
        else return varType.equals(VarType.String);
    }

    private boolean isBoolean(String line) {
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
