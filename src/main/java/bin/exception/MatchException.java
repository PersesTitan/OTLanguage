package bin.exception;

public class MatchException extends RuntimeException {
    private static final String grammarError = "문법이 일치하지 않습니다.";
    private static final String bracketMatchError = "중괄호 짝이 일치하지 않습니다.";
    private static final String loopStyleError = "해당 문법은 마지막에 '{'가 들어가야 합니다.";

    public MatchException(String message) {
        super(message);
    }

    public static void matchErrorMessage(MatchException e, String path, String line, long position) {
        String subMessage = switch (e.getMessage()) {
            case grammarError -> "The grammar does not match.\nPlease check the grammar.";
            case bracketMatchError -> "Curly braces don't match.\nPlease match the pair.";
            case loopStyleError -> "The grammar must have '{' at the end.\nPlease check the grammar again.";
            default -> "";
        };
        ErrorMessage.printErrorMessage(e, subMessage, path, line, position);
    }

    public static MatchException loopStyleError() {
        return new MatchException(loopStyleError);
    }

    public static MatchException bracketMatchError() {
        return new MatchException(bracketMatchError);
    }

    public static MatchException grammarError() {
        return new MatchException(grammarError);
    }
}
