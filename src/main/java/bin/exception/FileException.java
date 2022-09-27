package bin.exception;

import bin.apply.sys.item.SystemSetting;

public class FileException extends RuntimeException {
    private final static String noFindError = "파일을 찾을 수 없습니다.";
    private final static String pathNoHaveError = "해당 경로에 파일 및 디렉토리가 존재하지 않습니다.";
    private final static String noReadError = "파일을 읽을 수 없습니다.";
    private final static String isNotFileError = "파일만 열 수 있습니다.";
    private final static String rightExtension = "해당 확장자 형식을 읽을 수 없습니다.";
    private final static String alreadyExistsFileName = "이미 존재하는 파일명입니다.";

    public FileException(String message) {
        super(message);
    }

    public static void printErrorMessage(FileException e, String path) {
        String subMessage = switch (e.getMessage()) {
            case noFindError -> "File not found.\nPlease check the file location and path.";
            case noReadError -> "The file could not be read.\nPlease check if the contents of the file are corrupted.";
            case isNotFileError -> "Only files can be opened.\nPlease check if the path is a file.";
            case pathNoHaveError -> "The file and directory do not exist in that path.\nPlease check the path.";
            case rightExtension -> "The extension currently readable by OTLanguage is " + SystemSetting.getExtension() + ".";
            case alreadyExistsFileName -> "This is a file name that already exists.\nPlease change the file name.";
            default -> "";
        };
        if (path == null || path.isBlank()) ErrorMessage.printErrorMessage(e, subMessage);
        else ErrorMessage.printErrorMessage(e, subMessage, path);
    }

    public static void printErrorMessage(FileException e, String path, String line, long position) {
        if (e.getMessage().equals(pathNoHaveError))
            ErrorMessage.printErrorMessage(e, "The file and directory do not exist in that path.\nPlease check the path.", path, line, position);
        else printErrorMessage(e, path);

    }

    public static FileException alreadyExistsFileName() {
        return new FileException(alreadyExistsFileName);
    }

    public static FileException noFindError() {
        return new FileException(noFindError);
    }

    public static FileException pathNoHaveError() {
        return new FileException(pathNoHaveError);
    }

    public static FileException noReadError() {
        return new FileException(noReadError);
    }

    public static FileException isNotFileError() {
        return new FileException(isNotFileError);
    }

    public static FileException rightExtension() {
        return new FileException(rightExtension);
    }
}
