package bin.exception;

import java.util.StringTokenizer;

import static bin.apply.sys.item.Separator.SEPARATOR_LINE;

public class CosException extends RuntimeException {

    public CosException() {}

    public CosException(String message) {
        super(message);
    }

    public void cosErrorMessage(CosException e, String path, String line, long position) {
        String message = e.getMessage();
        int i = message.indexOf(SEPARATOR_LINE);
        String mes = message.substring(0, i);
        String subMessage = message.substring(i + 1);
        ErrorMessage.printErrorMessage(e.getClass().getSimpleName(), mes, subMessage, path, line, position);
    }
}
