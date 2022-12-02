package bin.apply.sys.make;

import java.io.File;

public interface OtherKlass {
    String IS_FILE = "";
    String CAN_EXECUTE = "?ㄷㅌㄷ";
    String CAN_READ = "?ㄹㄷㄹ";
    String CAN_WRITE = "?ㄹㅌㄹ";

    String COMPARE_TO = "";
    String CREATE_NEW_FILE = "?ㅅㅇㅅ";
    String DELETE = "ㅅㅈㅅ";
    String LIST = "ㄹㅅㄹ";

    default void start(Object ob, String works) {
        if (ob instanceof File file) {

        }
    }

    private Object startFile(File file, String works) {
        return switch (works) {
            case IS_FILE -> file.isFile();
            case CAN_EXECUTE -> file.canExecute();
            case CAN_READ -> file.canRead();
            default -> null;
        };
    }
}
