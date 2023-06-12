package cos.database;

import bin.exception.Error;
import bin.exception.ErrorTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum DatabaseException implements ErrorTool {
    CREATE_ERROR("생성에 실패하였습니다."),
    SQL_ERROR("SQL문 에러가 발생하였습니다."),
    SQL_RUN_ERROR("실행에 실패하였습니다.")
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case CREATE_ERROR ->
                    """
                    Creation failed.
                    Please try again.
                    """;
            case SQL_ERROR ->
                    """
                    An SQL statement error has occurred.
                    Please check the grammar again.
                    """;
            case SQL_RUN_ERROR ->
                    """
                    Execution failed.
                    Please check the grammar again.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
