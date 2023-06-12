package cos.math;

import bin.exception.Error;
import bin.exception.ErrorTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum MathException implements ErrorTool {
    RANDOM_BOUND_ERROR("값이 0이하 일 수 없습니다.")
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case RANDOM_BOUND_ERROR ->
                    """
                    Value cannot be less than zero.
                    Please enter a value greater than or equal to zero.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
