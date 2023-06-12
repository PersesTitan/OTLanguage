package cos.audio;

import bin.exception.Error;
import bin.exception.ErrorTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum AudioException implements ErrorTool {
    LOOP_VALUE_ERROR("반복은 1번 이상만 가능합니다."),
    ASSERTION_ERROR("값이 유효하지 않습니다.")
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case LOOP_VALUE_ERROR ->
                    """
                    You can repeat more than once.
                    Please change the value.
                    """;
            case ASSERTION_ERROR ->
                    """
                    The value is not valid.
                    Assertion failed.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
