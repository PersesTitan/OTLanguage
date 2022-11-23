package bin.exception;

import static bin.apply.sys.item.Separator.SEPARATOR_LINE;

public class CosException extends RuntimeException implements ExceptionMessage {

    public CosException() {}

    public CosException(String message) {
        super(message);
    }

    @Override
    public void errorMessage(RuntimeException e, String path, String line, long position) {
        String message = e.getMessage();
        int i = message.indexOf(SEPARATOR_LINE);
        String mes = message.substring(0, i);
        String subMessage = message.substring(i + 1);
        ErrorMessage.printErrorMessage(e.getClass().getSimpleName(), mes, subMessage, path, line, position);
    }
}
