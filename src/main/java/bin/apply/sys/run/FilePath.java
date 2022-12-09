package bin.apply.sys.run;

import bin.apply.Controller;
import bin.apply.Setting;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import work.v3.StartWorkV3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Map;

import static bin.apply.sys.item.Separator.SEPARATOR_FILE;
import static bin.apply.sys.item.Separator.SEPARATOR_LINE;
import static bin.apply.sys.item.SystemSetting.extension;
import static bin.apply.sys.item.SystemSetting.extensionCheck;
import static bin.apply.sys.make.StartLine.errorPath;
import static bin.token.Token.ACCESS;

public class FilePath extends StartWorkV3 {
    // 1
    public FilePath(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // 디렉토리~파일명
        String path = Setting.path + SEPARATOR_FILE + params[0].replace(ACCESS, SEPARATOR_FILE);
        for (String ext : extension) {
            File file = new File(path + ext);
            if (file.isFile()) {
                importFile(file, repositoryArray);
                return;
            }
        }
        throw new FileException().pathNoHaveError();
    }

    private void importFile(File file, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (!file.exists()) throw new FileException().pathNoHaveError();
        else if (!file.isFile()) throw new FileException().isNotFileError();
        else if (!file.canRead()) throw new FileException().noReadError();
        else if (!extensionCheck(file.getName())) throw new FileException().rightExtension();

        StringBuilder builder = new StringBuilder();
        // 파일 전체를 읽고 builder 에 값을 넣기
        Controller.readFile(file, builder);
        String path = errorPath.get();
        StartLine.startLine(builder.toString(), file.getAbsolutePath(), repositoryArray);
        errorPath.set(path);
    }
}
