package bin.exception;

import bin.token.EditToken;

public interface ErrorTool {
    String getMessage();
    String getSubMessage();                 // 지속적으로 추가
    Error getThrow(String error);

    default Error getThrow(Object error) {
        return getThrow(EditToken.toString(error));
    }
}
