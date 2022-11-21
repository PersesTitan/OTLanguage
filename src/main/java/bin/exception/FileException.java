package bin.exception;

import bin.apply.sys.item.SystemSetting;

import static bin.apply.sys.item.Separator.SYSTEM_PATH;

public class FileException extends RuntimeException {
    private final String noFindError = "파일을 찾을 수 없습니다.";
    private final String pathNoHaveError = "해당 경로에 파일 및 디렉토리가 존재하지 않습니다.";
    private final String noReadError = "파일을 읽을 수 없습니다.";
    private final String isNotFileError = "파일만 열 수 있습니다.";
    private final String rightExtension = "해당 확장자 형식을 읽을 수 없습니다.";
    private final String alreadyExistsFileName = "이미 존재하는 파일명입니다.";
    private final String addModuleError = "모듈 추가에 실패하였습니다.";
    private final String noValidValues = "유효한 값을 받지 못하였습니다.";
    private final String didNotReadSystemFile = "system.otls파일을 읽어올 수 없습니다.";

    public FileException() {}

    public FileException(String message) {
        super(message);
    }

    public void printErrorMessage(FileException e, String path) {
        String subMessage = switch (e.getMessage()) {
            case noFindError -> "File not found.\nPlease check the file location and path.";
            case noReadError -> "The file could not be read.\nPlease check if the contents of the file are corrupted.";
            case isNotFileError -> "Only files can be opened.\nPlease check if the path is a file.";
            case pathNoHaveError -> "The file and directory do not exist in that path.\nPlease check the path.";
            case rightExtension -> "The extension currently readable by OTLanguage is (" + SystemSetting.getExtension() + ").";
            case alreadyExistsFileName -> "This is a file name that already exists.\nPlease change the file name.";
            case addModuleError -> "Failed to add module.\nPlease check if there is a module file.";
            case noValidValues -> "No valid values were received.\nPlease try again or reinstall.";
            case didNotReadSystemFile -> "Unable to read system.otls file.\nPlease check if the file is present and check if it is damaged.\nPath : " + SYSTEM_PATH;
            default -> "";
        };
        if (path == null || path.isBlank()) ErrorMessage.printErrorMessage(e, subMessage);
        else ErrorMessage.printErrorMessage(e, subMessage, path);
    }

    public void printErrorMessage(FileException e, String path, String line, long position) {
        if (e.getMessage().equals(pathNoHaveError))
            ErrorMessage.printErrorMessage(e, "The file and directory do not exist in that path.\nPlease check the path.", path, line, position);
        else printErrorMessage(e, path);
    }

    public FileException didNotReadSystemFile() {
        return new FileException(didNotReadSystemFile);
    }

    public FileException noValidValues() {
        return new FileException(noValidValues);
    }

    public FileException addModuleError() {
        return new FileException(addModuleError);
    }

    public FileException alreadyExistsFileName() {
        return new FileException(alreadyExistsFileName);
    }

    public FileException noFindError() {
        return new FileException(noFindError);
    }

    public FileException pathNoHaveError() {
        return new FileException(pathNoHaveError);
    }

    public FileException noReadError() {
        return new FileException(noReadError);
    }

    public FileException isNotFileError() {
        return new FileException(isNotFileError);
    }

    public FileException rightExtension() {
        return new FileException(rightExtension);
    }
}
