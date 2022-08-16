package origin.exception;

public interface VariableMessage {
    String typeMatchError = "변수 타입이 일치하지 않습니다.";
    String sameVariable = "은(는) 이미 존재하는 변수명 입니다.";
    String sameMethod = "은(는) 이미 존재하는 클래스명/메소드명 입니다.";
    String doNotFindClass = "은(는) 존재하지않는 클래스명 입니다.";
    String doNotFindMethod = "은(는) 존재하지않는 메소드명 입니다.";
    String doNotFind = "은(는) 정의되지 않은 변수 입니다.";
    String doNotDefine = "은(는) 정의 되지 않은 타입입니다.";
    String noHaveInitial = "초기값이 존재하지 않습니다.";
    String noHaveVarName = "변수명이 존재하지 않습니다.";
}
