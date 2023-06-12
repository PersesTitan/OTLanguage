package cos.gui;

import bin.exception.Error;
import bin.exception.ErrorTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum GuiException implements ErrorTool {
    DO_NOT_SUPPORT_TYPE("지원하지 않는 종류입니다."),
    DO_NOT_USE_TYPE("해당 타입에서는 지원하지 않습니다.")
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case DO_NOT_SUPPORT_TYPE ->
                    """
                    This type is not supported.
                    Please check this name.
                    """;
            case DO_NOT_USE_TYPE ->
                    """
                    This type does not support it.
                    Please check the item type.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
