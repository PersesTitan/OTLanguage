package bin.exception;

public class ServerException extends RuntimeException implements ExceptionMessage {
    private final String fileReadError = "파일을 읽을 수 없습니다.";
    private final String serverReadError = "서버 페이지를 생성할 수 없습니다.";
    private final String noHaveError = "서버가 존재하지 않습니다.";

    public ServerException() {}

    public ServerException(String message) {
        super(message);
    }

    @Override
    public void errorMessage(RuntimeException e, String path, String line, long position) {
        String subMessage = switch (e.getMessage()) {
            case fileReadError -> "The file could not be read.\nplease try again.";
            case serverReadError -> "Could not create server page.\nplease try again.";
            case noHaveError -> "The server does not exist.";
            default -> "";
        };
        ErrorMessage.printErrorMessage(e, subMessage, path, line, position);
    }

    public ServerException noHaveError() {
        return new ServerException().noHaveError();
    }

    public ServerException serverReadError() {
        return new ServerException(serverReadError);
    }

    public ServerException fileReadError() {
        return new ServerException(fileReadError);
    }
}
