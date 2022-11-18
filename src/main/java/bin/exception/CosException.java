package bin.exception;

import java.util.StringTokenizer;

public class CosException extends RuntimeException {

    public CosException() {}

    public CosException(String message) {
        super(message);
    }

    public void cosErrorMessage(CosException e, String path, String line, long position) {
        StringTokenizer tokenizer = new StringTokenizer(e.getMessage(), "`");
        String mes = tokenizer.nextToken();
        String subMessage = tokenizer.nextToken();
        ErrorMessage.printErrorMessage(e.getClass().getSimpleName(), mes, subMessage, path, line, position);
    }
}
