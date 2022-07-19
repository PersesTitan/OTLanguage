package origin.exception;

public class FileFailException extends RuntimeException {
    public FileFailException() {
        super();
    }

    public FileFailException(String message) {
        super(message);
    }

    public FileFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileFailException(Throwable cause) {
        super(cause);
    }
}
