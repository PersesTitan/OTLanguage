package bin.apply.sys.run;

import bin.apply.Setting;
import bin.apply.sys.item.Separator;
import bin.apply.sys.make.StartLine;
import bin.exception.FileException;
import bin.token.LoopToken;
import work.StartWork;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bin.apply.sys.item.Separator.SEPARATOR_FILE;
import static bin.apply.sys.item.Separator.SEPARATOR_LINE;
import static bin.apply.sys.item.SystemSetting.extension;
import static bin.apply.sys.item.SystemSetting.extensionCheck;
import static bin.apply.sys.make.StartLine.errorPath;

public class FilePath implements LoopToken, StartWork {
    private final Matcher matcher;

    public FilePath() {
        String patternText = startEndMerge(FILE, BLANKS, FILE_TYPE, "(", ACCESS, FILE_TYPE, ")*");
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        StringTokenizer tokenizer = new StringTokenizer(line.strip());
        tokenizer.nextToken();                  // ㅍㅅㅍ
        // 디렉토리~파일명
        String path = Setting.path + SEPARATOR_FILE + tokenizer
                        .nextToken()
                        .replace(ACCESS, SEPARATOR_FILE);

        for (String ext : extension) {
            File file = new File(path + ext);
            if (file.isFile()) {
                importFile(file, repositoryArray);
                return;
            }
        }
        throw FileException.pathNoHaveError();
    }

    @Override
    public void first() {

    }

    private void importFile(File file, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        if (!file.exists()) throw FileException.pathNoHaveError();
        else if (!file.isFile()) throw FileException.isNotFileError();
        else if (!file.canRead()) throw FileException.noReadError();
        else if (!extensionCheck(file.getName())) throw FileException.rightExtension();

        StringBuilder builder = new StringBuilder();
        try (FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(fileReader)) {
            for (int i=1;;i++) {
                String line = reader.readLine();
                if (line == null) break;
                builder.append(i).append(" ").append(line).append(SEPARATOR_LINE);
            }
        } catch (IOException ignored) {}
        String path = errorPath.get();
        StartLine.startLine(builder.toString(), file.getAbsolutePath(), repositoryArray);
        errorPath.set(path);
    }
}
