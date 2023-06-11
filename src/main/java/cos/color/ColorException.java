package cos.color;

import bin.exception.Error;
import bin.exception.ErrorTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum ColorException implements ErrorTool {
    COLOR_VALUE_ERROR("유효하지 않는 값입니다.")
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case COLOR_VALUE_ERROR ->
                    """
                    Invalid value.
                    Values range from 0 to 255.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
