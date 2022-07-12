package origin.exception;

public class MatchException extends RuntimeException {

    public MatchException() {
        super();
    }

    public MatchException(String message) {
        super(message);
    }

    public MatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchException(Throwable cause) {
        super(cause);
    }
}
