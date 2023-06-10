package bin.exception;

import bin.token.KlassToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VariableException implements ErrorTool {
    VALUE_ERROR("변수 값이 유효하지 않습니다."),
    DEFINE_NAME("이미 정의된 이름 입니다."),
    NO_DEFINE_TYPE("정의되지 않은 변수타입입니다."),
    NO_DEFINE_NAME("생성할 수 없는 이름입니다."),
    NO_CREATE_VARIABLE("정의되지 않는 변수입니다."),
    NO_METHOD("정의되지 않는 메소드입니다."),
    NO_CLASS("정의되지 않는 클래스입니다."),
    ACCESS_ERROR("해당 위치에 접근할 수 없습니다."),
    TYPE_DIFFERENT("타입이 일치하지 않습니다."),
    TYPE_ERROR("변수 타입이 유효하지 않습니다."),
    KLASS_NAME("변수명이 클래스명과 동일합니다."),
    RESERVED_USE("예약어를 이름으로 사용할 수 없습니다."),
    INPUT_VALUE_ERROR("입력 받은 값이 유효하지 않습니다."),
    NO_RETURN_TYPE("리턴 값이 존재하지 않습니다."),
    STATIC_NAME_ERROR(KlassToken.STATIC_METHOD + "메소드 이름과 동일할 수 없습니다."),
    DO_NOT_CREATE_KLASS("생성할 수 없는 클래스입니다."),
    ;

    private final String message;

    @Override
    public String getSubMessage() {
        return switch (this) {
            case DO_NOT_CREATE_KLASS ->
                    """
                    The class cannot be created.
                    Please remove the code you are generating.
                    """;
            case NO_RETURN_TYPE ->
                    """
                    Return value does not exist.
                    Please check the return type.
                    """;
            case INPUT_VALUE_ERROR ->
                    """
                    The value entered is invalid.
                    """;
            case RESERVED_USE ->
                    """
                    Reservation cannot be used as a name.
                    Please change the name.
                    """;
            case KLASS_NAME ->
                    """
                    The variable name is the same as the class name.
                    Please check your name.
                    """;
            case STATIC_NAME_ERROR ->
                    """
                    The method name cannot be the same.
                    Please change the name.
                    """;
            case TYPE_ERROR ->
                    """
                    The variable type is invalid.
                    Please check the variable type.
                    """;
            case TYPE_DIFFERENT ->
                    """
                    Type does not match.
                    Classes with different parameters must match in type.
                    """;
            case VALUE_ERROR ->
                    """
                    The variable value is invalid.
                    Please check the variable type.
                    """;
            case DEFINE_NAME ->
                    """
                    Name already defined.
                    Reserved words or already used names cannot be reused.
                    """;
            case NO_DEFINE_TYPE ->
                    """
                    Undefined variable type.
                    Please check the variable type.
                    """;
            case NO_METHOD ->
                    """
                    Undefined method name.
                    Please check the method name.
                    """;
            case NO_CLASS ->
                    """
                    Undefined class name.
                    Please check the class name.
                    """;
            case NO_CREATE_VARIABLE ->
                    """
                    Undefined variable name.
                    Please check the variable name.
                    """;
            case NO_DEFINE_NAME ->
                    """
                    The name cannot be created.
                    Reserved words or already used names cannot be reused.
                    """;
            case ACCESS_ERROR ->
                    """
                    The location is inaccessible.
                    Please check your current location.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
