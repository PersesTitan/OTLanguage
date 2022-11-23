package bin.exception;

public interface ExceptionMessage {
    void errorMessage(RuntimeException e, String path, String line, long position);
}
