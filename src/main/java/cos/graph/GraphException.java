package cos.graph;

import bin.exception.Error;
import bin.exception.ErrorTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum GraphException implements ErrorTool {
    NO_HAVE_ITEM("추가한 식이 존재하지 않습니다.")
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case NO_HAVE_ITEM ->
                    """
                    The expression you added does not exist.
                    Please add an expression.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
