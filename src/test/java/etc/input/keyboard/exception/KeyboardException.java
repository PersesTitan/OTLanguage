package etc.input.keyboard.exception;

import bin.exception.CosException;

public class KeyboardException extends CosException {

    public KeyboardException() {}

    public KeyboardException(String message) {
        super(message);
    }

    public KeyboardException awtError() {
        String awtError = "화면, 마우스, 키보드 등의 접근이 불가능합니다. `"
                + "Screen, mouse, keyboard, etc. are not available."
                + "\nPlease check the screen, mouse, and keyboard permissions and status.";
        return new KeyboardException(awtError);
    }

    public KeyboardException securityException() {
        String errorMessage = "생성 권한이 부여되지 않았습니다. `" +
                "Create permission not granted.\n" +
                "Please retry after granting permission.";
        return new KeyboardException(errorMessage);
    }
}
