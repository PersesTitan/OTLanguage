package origin.exception;

public class VariableException extends RuntimeException {

    public VariableException() {
        super();
    }

    public VariableException(String message) {
        super(message);
    }

    public VariableException(String message, Throwable cause) {
        super(message, cause);
    }

    public VariableException(Throwable cause) {
        super(cause);
    }
}
