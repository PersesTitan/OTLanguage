package bin.exception;

import bin.apply.mode.DebugMode;
import bin.apply.mode.FileMode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileException implements ErrorTool {
    DO_NOT_INCLUDE("클래스 추가에 실패하였습니다."),
    ADD_FAIL_ERROR("모듈을 추가하는데 실패하였습니다."),
    DO_NOT_READ("파일을 읽을 수 없습니다."),
    CREATE_FILE_ERROR("파일 생성하는데 실패하였습니다."),
    CREATE_ICON_ERROR("아이콘을 생성에 실패하였습니다."),
    DO_NOT_SUPPORT("지원하지 않는 확장자 입니다."),
    DO_NOT_PATH("해당 경로에 파일 및 디렉토리가 존재하지 않습니다."),
    FILE_TYPE_ERROR("파일 타입만 열 수 있습니다."),
    DO_NOT_FIND("파일을 찾을 수 없습니다."),
    ;

    private final String message;


    @Override
    public String getSubMessage() {
        return switch (this) {
            case DO_NOT_FIND ->
                    """
                    File not found.
                    Please check the file location and path.
                    """;
            case FILE_TYPE_ERROR ->
                    """
                    Only files can be opened.
                    Please check if the path is a file.
                    """;
            case DO_NOT_PATH ->
                    """
                    The file and directory do not exist in that path.
                    Please check the path.
                    """;
            case DO_NOT_SUPPORT ->
                    """
                    Unsupported extension.
                    Please make sure that it matches the following extensions.
                    """.strip() + FileMode.extensionList();
            case CREATE_ICON_ERROR ->
                    """
                    Failed to create icon.
                    Please check the "icon.otlm" file.
                    """;
            case CREATE_FILE_ERROR ->
                    """
                    Failed to create file.
                    Please check the file location and path.
                    """;
            case DO_NOT_READ ->
                    """
                    The file could not be read.
                    Please check if the contents of the file are corrupted.
                    """;
            case DO_NOT_INCLUDE ->
                    """
                    Failed to add class.
                    Please download the file again or try again.
                    """;
            case ADD_FAIL_ERROR ->
                    """
                    Failed to add module.
                    Please try again.
                    """;
        };
    }

    @Override
    public Error getThrow(String error) {
        return new Error(this, error);
    }
}
