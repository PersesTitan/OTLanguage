package cos.ocr;

import bin.exception.Error;
import bin.exception.ErrorTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum OCRException implements ErrorTool {
    DO_NOT_FIND_LANGUAGE("존재하지 않는 언어 데이터 입니다."),
    NOT_HAVE_LANGUAGE("설정된 언어가 존재하지 않습니다."),
    NOT_INTI("언어 초기화가 되지 않았습니다. " + OCRToken.INIT)
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case NOT_INTI ->
                    """
                    Language initialization failed.
                    Please reset the language.
                    """;
            case DO_NOT_FIND_LANGUAGE ->
                    """
                    Language data does not exist.
                    Please check the language name.
                    """;
            case NOT_HAVE_LANGUAGE ->
                    """
                    The language set does not exist.
                    Please add a language.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
