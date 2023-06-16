package cos.time;

import bin.exception.Error;
import bin.exception.ErrorTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeException implements ErrorTool {
    VALUE_VALID("값이 유효하지 않습니다.")
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case VALUE_VALID ->
                    """
                    The value is not valid.
                    Please check the value.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
