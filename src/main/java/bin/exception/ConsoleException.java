package bin.exception;

public class ConsoleException extends RuntimeException {
    private final static String scannerError = "입력에 실패하였습니다.";
    private final static String printError = "출력에 실패하였습니다.";

    public ConsoleException(String message) {
        super(message);
    }

    public static void consoleErrorMessage(ConsoleException e, String path, String line, long position) {
        String subMessage = switch (e.getMessage()) {
            case scannerError -> "Input failed.\nplease try again.";
            case printError -> "Output failed.\nplease try again.";
            default -> "";
        };
        ErrorMessage.printErrorMessage(e, subMessage, path, line, position);
    }

    public static ConsoleException printError() {
        return new ConsoleException(printError);
    }

    public static ConsoleException scannerError() {
        return new ConsoleException(scannerError);
    }
}
