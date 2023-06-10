package bin.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StringException implements ErrorTool {
    INDEX_ERROR("길이 유효하지 않습니다.")
    ;
    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case INDEX_ERROR ->
                    """
                    The length is invalid.
                    Please check the length.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
