package origin.exception;

public class RandomException extends RuntimeException {
    public RandomException() {
        super();
    }

    public RandomException(String message) {
        super(message);
    }

    public RandomException(String message, Throwable cause) {
        super(message, cause);
    }

    public RandomException(Throwable cause) {
        super(cause);
    }
}
