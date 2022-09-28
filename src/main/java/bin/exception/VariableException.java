package bin.exception;

public class VariableException extends RuntimeException {
    private final static String typeMatch = "변수 값이 유효하지 않습니다.";
    private final static String sameVariable = "이미 존재하는 변수명입니다.";
    private final static String localNoVariable = "해당 위치의 변수에 접근할 수 없습니다.";
    private final static String reservedWorks = "예약된 단어를 사용할 수 없습니다.";
    private final static String noDefine = "정의되지 않은 변수 타입 입니다.";
    private final static String noGrammar = "문법이 올바르지 않습니다.";
    private final static String forTypeMatchError = "^^문의 타입으로 유효하지 않습니다.";

    public VariableException(String message) {
        super(message);
    }

    public static void variableErrorMessage(VariableException e, String path, String line, long position) {
        String subMessage = switch (e.getMessage()) {
            case typeMatch -> "The variable value is invalid.\nPlease check the variable type.";
            case sameVariable -> "Variable name that already exists.\nReserved words or already used variable names cannot be reused.";
            case localNoVariable -> "You cannot access the variable at that location.\nPlease check your current location.";
            case reservedWorks -> "Reserved words cannot be used.";
            case noDefine -> "Undefined variable name.\nPlease check the variable name.";
            case noGrammar -> "The grammar does not match.\nPlease check the grammar.";
            case forTypeMatchError -> "^^ It is not valid as a return value of the query.\nPlease check the variable type";
            default -> "";
        };
        ErrorMessage.printErrorMessage(e, subMessage, path, line, position);
    }

    public static VariableException forTypeMatchError() {
        return new VariableException(forTypeMatchError);
    }

    public static VariableException noGrammar() {
        return new VariableException(noGrammar);
    }

    public static VariableException typeMatch() {
        return new VariableException(typeMatch);
    }

    public static VariableException sameVariable() {
        return new VariableException(sameVariable);
    }

    public static VariableException localNoVariable() {
        return new VariableException(localNoVariable);
    }

    public static VariableException reservedWorks() {
        return new VariableException(reservedWorks);
    }

    public static VariableException noDefine() {
        return new VariableException(noDefine);
    }

}
