package bin.exception;

import java.util.regex.Pattern;

public class MatchException extends RuntimeException {
    private final String grammarError = "문법이 일치하지 않습니다.";
    private final String bracketMatchError = "중괄호 짝이 일치하지 않습니다.";
    private final String loopStyleError = "해당 문법은 마지막에 '{'가 들어가야 합니다.";
    private final String patternMatchError1 = "%s는 이미 존재하거나 규칙에 일치하지 않기 때문에 사용할 수 없습니다.";
    private final String patternMatchError2 = "규칙에 일치하지 않기 때문에 사용할 수 없습니다.";

    public MatchException() {}

    public MatchException(String message) {
        super(message);
    }

    public void matchErrorMessage(MatchException e, String path, String line, long position) {
        String subMessage = switch (e.getMessage()) {
            case grammarError -> "The grammar does not match.\nPlease check the grammar.";
            case bracketMatchError -> "Curly braces don't match.\nPlease match the pair.";
            case loopStyleError -> "The grammar must have '{' at the end.\nPlease check the grammar again.";
            default -> (e.getMessage().equals(patternMatchError2)
                    ? ""
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
