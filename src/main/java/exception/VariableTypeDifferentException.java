package exception;

public class VariableTypeDifferentException extends RuntimeException {

    public VariableTypeDifferentException() {
        super();
    }

    public VariableTypeDifferentException(String message) {
        super(message);
    }

    public VariableTypeDifferentException(String message, Throwable cause) {
        super(message, cause);
    }

    public VariableTypeDifferentException(Throwable cause) {
        super(cause);
    }

    protected VariableTypeDifferentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
