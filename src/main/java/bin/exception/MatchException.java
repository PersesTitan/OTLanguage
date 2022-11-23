package bin.exception;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static bin.calculator.tool.CalculatorTool.compare;
import static bin.token.LoopToken.ELSE_IF;
import static bin.token.cal.BoolToken.*;

public class MatchException extends RuntimeException implements ExceptionMessage {
    private final String grammarError = "문법이 일치하지 않습니다.";
    private final String bracketMatchError = "중괄호 짝이 일치하지 않습니다.";
    private final String loopStyleError = "해당 문법은 마지막에 '{'가 들어가야 합니다.";
    private final String patternMatchError1 = "%s는 이미 존재하거나 규칙에 일치하지 않기 때문에 사용할 수 없습니다.";
    private final String patternMatchError2 = "규칙에 일치하지 않기 때문에 사용할 수 없습니다.";
    private final String dateFormatError = "Date Format 형식에 일치하지 않습니다.";
    private final String timeFormatError = "Time Format 형식에 일치하지 않습니다.";
    private final String grammarTypeError = "타입 또는 문법이 유효하지 않습니다.";
    private String grammarTypeToken = null;
    private GrammarTypeClass grammarTypeClass;

    public MatchException() {}

    public MatchException(String message) {
        super(message);
    }

    @Override
    public void errorMessage(RuntimeException e, String path, String line, long position) {
        String subMessage = switch (e.getMessage()) {
            case grammarError -> "The grammar does not match.\nPlease check the grammar.";
            case bracketMatchError -> "Curly braces don't match.\nPlease match the pair.";
            case loopStyleError -> "The grammar must have '{' at the end.\nPlease check the grammar again.";
            case dateFormatError -> "Date Format does not match the format.\nPlease check the format.";
            case timeFormatError -> "Time Format does not match the format.\nPlease check the format.";
            case grammarTypeError -> grammarTypeErrorMessage(grammarTypeClass);
            default -> (e.getMessage().equals(patternMatchError2) ? ""
                    : "Error Pattern (" + e.getMessage().replaceFirst(Pattern.quote(patternMatchError1.substring("%s".length())), "") + ")\n")
                        + "Hint: Cannot use special characters (url에서는 특수문자는 사용할 수 없습니다.)\n"
                        + "It cannot be used because it already exists or does not match the rule.\n"
                        + "Please try to change the name.";
        };
        ErrorMessage.printErrorMessage(e, subMessage, path, line, position);
    }

    public MatchException patternMatchError(String error) {
        return new MatchException(
                error == null
                ? patternMatchError2
                : String.format(patternMatchError1, error));
    }

    public MatchException grammarTypeError(String token, GrammarTypeClass grammarTypeClass) {
        this.grammarTypeToken = token;
        this.grammarTypeClass = grammarTypeClass;
        return new MatchException(grammarTypeError);
    }

    private String grammarTypeErrorMessage(GrammarTypeClass grammarTypeClass) {
        String message = "The type or grammar is invalid.\nPlease check the price.";
        if (grammarTypeToken != null) {
            message = message
                    .concat("\nDoesn't match (" + grammarTypeToken + ")\n")
                    .concat(switch (grammarTypeClass) {
                        case OR_AND -> String.format("Does not match token (%s, %s).", OR, AND);
                        case TRUE_FALSE -> String.format("Does not match token (%s, %s).", TRUE, FALSE);
                        case COMPARE -> String.format("Does not match token (%s).", String.join(", ", compare.keySet()));
                        case NO_ELSE_IF -> String.format("Does not match token (%s).", ELSE_IF);
                        case TOKEN -> "Incomprehensible token";
                        case NUMBER -> "It's not a number.";
                        case VALID -> "The form is not valid.";
                    });
            grammarTypeToken = null;
        }
        return message;
    }

    public enum GrammarTypeClass {
        OR_AND, NUMBER, TRUE_FALSE, COMPARE, TOKEN, VALID, NO_ELSE_IF
    }

    public MatchException dateFormatError() {
        return new MatchException(dateFormatError);
    }

    public MatchException timeFormatError() {
        return new MatchException(timeFormatError);
    }

    public MatchException loopStyleError() {
        return new MatchException(loopStyleError);
    }

    public MatchException bracketMatchError() {
        return new MatchException(bracketMatchError);
    }

    public MatchException grammarError() {
        return new MatchException(grammarError);
    }
}
