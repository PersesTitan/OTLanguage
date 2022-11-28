package etc.klass;

import java.io.File;
import java.lang.reflect.Field;

import static bin.token.StringToken.JOIN;

public interface OtherKlassTest {
    /**
     * GET : ㄱㅅㄱ <br>
     * START : ㅅㅌㅅ
     */
    // FILE
    String FILE = "ㅍㅅㅍ";
    String FILE_GET_NAME = ".";
    String FILE_GET_PARENT = "..";
    String FILE_GET_PATH = "...";
    String FILE_GET_ABSOLUTE_PATH = "....";
    String FILE_IS_ABSOLUTE = ".....";
    // STRING

    default Object getFinal(Object value, String[] methods, Object[][] params) {

        return value;
    }

    private Object work(Object value, String method, Object[] params) {
        if (value instanceof File v) return getFile(v, method);

        return null;
    }

    // FILE
    private Object getFile(File v, String method) {
        return switch (method) {
            case FILE_GET_NAME -> v.getName();
            case FILE_GET_PARENT -> v.getParent();
            case FILE_GET_PATH -> v.getPath();
            case FILE_GET_ABSOLUTE_PATH -> v.getAbsolutePath();
            case FILE_IS_ABSOLUTE -> v.isAbsolute();
            default -> null;
        };
    }
}
