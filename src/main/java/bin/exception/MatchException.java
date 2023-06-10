package bin.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchException implements ErrorTool {
    GRAMMAR_ERROR("문법이 일치하지 않습니다."),
    NUMBER_GRAMMAR_ERROR("계산 문법이 유효하지 않습니다."),
    ZONE_MATCH_ERROR("괄호가 일치하지 않아 존을 사용할 수 없습니다."),
    PARAM_COUNT_ERROR("파라미터 길이가 유효하지 않습니다."),
    CREATE_KLASS_ERROR("해당 위치에서 클래스를 생성할 수 없습니다."),
    CREATE_METHOD_ERROR("해당 위치에서 메소드를 생성 할 수 없습니다."),
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case NUMBER_GRAMMAR_ERROR ->
                    """
                    Calculation grammar is not valid.
                    Please check the grammar again.
                    """;
            case CREATE_METHOD_ERROR ->
                    """
                    Cannot create a method from that location.
                    Please check the grammar again.
                    """;
            case CREATE_KLASS_ERROR ->
                    """
                    Cannot create a class from that location.
                    Please check the grammar again.
                    """;
            case GRAMMAR_ERROR ->
                    """
                    The grammar does not match.
                    Please check the grammar.
                    """;
            case PARAM_COUNT_ERROR ->
                    """
                    Parameter length is invalid.
                    Please check the parameter length.
                    """;
            case ZONE_MATCH_ERROR ->
                    """
                    Zone is not available because parentheses do not match.
                    Please check the grammar.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
