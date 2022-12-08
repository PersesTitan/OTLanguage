package bin.exception;

public class ConsoleException extends RuntimeException implements ExceptionMessage {
    private final static String scannerError = "입력에 실패하였습니다.";
    private final static String printError = "출력에 실패하였습니다.";

    public ConsoleException() {}

    public ConsoleException(String message) {
        super(message);
    }

    @Override
    public void errorMessage(RuntimeException e, String path, String line, long position) {
        String subMessage = switch (e.getMessage()) {
            case scannerError -> "Input failed.\nplease try again.";
            case printError -> "Output failed.\nplease try again.";
            default -> "";
        };
        ErrorMessage.printErrorMessage(e, subMessage, path, line, position);
    }

    public ConsoleException printError() {
        return new ConsoleException(printError);
    }

    public ConsoleException scannerError() {
        return new ConsoleException(scannerError);
    }
}
