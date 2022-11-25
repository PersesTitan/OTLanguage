package etc.music.exception;

import bin.exception.CosException;

public class MusicException extends CosException {

    public MusicException() {}

    public MusicException(String message) {
        super(message);
    }

    public MusicException doNotReadMusicFile() {
        String message = """
                음원 파일을 찾을 수 없습니다.
                The sound source file could not be found.
                Please check if it is installed properly.
                """;
        return new MusicException(message);
    }

    public MusicException methodTypeError() {
        String message = """
                메소드 타입이 일치하지 않습니다.
                The method type does not match.
                Please check the method type.
                """;
        return new MusicException(message);
    }

    public MusicException noDefine() {
        String message = """
                정의되지 않은 이름입니다.
                Undefined name.
                Please define it first.
                """;
        return new MusicException(message);
    }

    public MusicException defineName() {
        String message = """
                이미 정의된 이름입니다.
                The name is already defined.
                Please change the name.
                """;
        return new MusicException(message);
    }

    public MusicException nameMathError() {
        String message = """
                유효한 이름이 아닙니다.
                This is not a valid name.
                Please change the name.
                """;
        return new MusicException(message);
    }

    public MusicException loopCountError() {
        String message = """
                반복은 1번 이상만 가능합니다.
                You can repeat more than once.
                Please change the value.
                """;
        return new MusicException(message);
    }
}
