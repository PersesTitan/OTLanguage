package cos.poison;

import bin.exception.Error;
import bin.exception.ErrorTool;
import bin.token.KlassToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PoisonException implements ErrorTool {
    PARAMS_SIZE_ERROR("파라미터 길이가 유효하지 않습니다."),
    URL_TYPE_ERROR("URL에 적용할 수 있는 타입이 아닙니다."),
    URL_NAME_ERROR("URL에 적용되는 변수명은 영어와 숫자만 사용할 수 있습니다."),
    URL_USE_ERROR("사용되지 않은 URL 변수명이 존재합니다."),
    RESPONSE_TYPE_ERROR("전송 타입이 유효하지 않습니다."),
    DATA_TYPE_ERROR("데이터 타입이 유효하지 않습니다."),
    CHARSET_TYPE_ERROR("문자 인코딩 타입이 유효하지 않습니다."),

    CREATE_SERVER_ERROR("서버 생성에 실패하였습니다."),
    NO_CREATE_SERVER("서버가 생성되어 있지 않습니다."),
    DO_NOT_READ("데이터를 읽는데 실패하였습니다."),
    DATA_PATTERN_ERROR("데이터 값이 유효하지 않습니다."),
    HAVE_PATH_ERROR("이미 정의된 경로입니다."),
    METHOD_TYPE_ERROR("지원하지 않는 메소드 타입입니다."),
    URL_REGEXP_ERROR("URL 변수값이 유효하지 않습니다."),
    URL_FIND_ERROR("URL 안에 일치하는 이름이 존재하지 않습니다."),
    DO_NOT_RUN("해당 위치에서 실행할 수 없습니다."),
    IN_DATA_TYPE_ERROR("지원하지 않는 데이터 타입입니다.")
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case CHARSET_TYPE_ERROR ->
                    """
                    Invalid charset type.
                    Please check the charset type.
                    """;
            case DATA_TYPE_ERROR ->
                    """
                    Invalid data type.
                    Please check the data type.
                    """;
            case RESPONSE_TYPE_ERROR ->
                    """
                    Invalid transport type.
                    Please check the response type.
                    """;

            case URL_USE_ERROR ->
                    """
                    Unused URL variable name exists.
                    Please check the url rule.
                    """;
            case URL_NAME_ERROR ->
                    """
                    Variable names that apply to URLs can only be English and numeric.
                    However, you cannot start with a number.
                    """;
            case PARAMS_SIZE_ERROR ->
                    """
                    Parameter length is not valid.
                    Please check the entered value.
                    """;
            case URL_TYPE_ERROR ->
                    """
                    This type is not applicable to the URL.
                    Please check the following types. (
                    """.strip() + String.join(", ", KlassToken.ORIGINS) + ')';
            case IN_DATA_TYPE_ERROR ->
                    """
                    Unsupported data type.
                    Please check the value.
                    """;
            case DO_NOT_RUN ->
                    """
                    Unable to run from that location.
                    Please check the grammar.
                    """;
            case URL_FIND_ERROR ->
                    """
                    No matching name exists in the URL.
                    Please add the value.
                    """;
            case URL_REGEXP_ERROR ->
                    """
                    The url variable value is invalid.
                    Please change the name.
                    """;
            case METHOD_TYPE_ERROR ->
                    """
                    This method type is not supported.
                    Please change the method type.
                    """;
            case HAVE_PATH_ERROR ->
                    """
                    Path already defined.
                    Please change the path value.
                    """;
            case DATA_PATTERN_ERROR ->
                    """
                    The data value is invalid.
                    Please check the data.
                    """;
            case CREATE_SERVER_ERROR ->
                    """
                    Server creation failed.
                    Please check the server permission and status.
                    """;
            case NO_CREATE_SERVER ->
                    """
                    No server has been created.
                    Please create a server.
                    """;
            case DO_NOT_READ ->
                    """
                    Failed to read data.
                    Please try again.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
