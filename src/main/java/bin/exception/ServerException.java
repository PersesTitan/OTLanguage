package bin.exception;

public class ServerException extends RuntimeException {
    private final static String fileReadError = "파일을 읽을 수 없습니다.";
    private final static String serverReadError = "서버 페이지를 생성할 수 없습니다.";

    public ServerException(String message) {
        super(message);
    }

    public static void serverErrorMessage(ServerException e, String path, String line, long position) {
        String subMessage = switch (e.getMessage()) {
            case fileReadError -> "The file could not be read.\nplease try again.";
            case serverReadError -> "Could not create server page.\nplease try again.";
            default -> "";
        };
        ErrorMessage.printErrorMessage(e, subMessage, path, line, position);
    }

    public static ServerException serverReadError() {
        return new ServerException(serverReadError);
    }

    public static ServerException fileReadError() {
        return new ServerException(fileReadError);
    }
}
