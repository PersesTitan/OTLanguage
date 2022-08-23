package etc.reader.controller;

import etc.reader.define.FileWork;
import event.Setting;
import origin.exception.FileFailException;
import origin.exception.FileFailMessage;
import system.start.OSSetting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRead implements FileWork {
    private final String text; // ㅍㅅㅍ
    private final String patternText; // ㅍㅅㅍ 디렉토리명/파일명.otl
    private final Pattern pattern;

    public FileRead(String patternText) {
        this.text = patternText;
        // ㅍㅅㅍ 디렉토리명/파일명.otl
        this.patternText = "^\\s*" + patternText + "\\s+[\\s\\S]+";
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        String slash = OSSetting.sep(); // 슬래쉬 반환
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String group = matcher.group().replace("~", OSSetting.sep());
            group = group.replaceFirst("^\\s*" + text, "").strip(); // 디렉토리명/파일명.otl
            String path = new File(Setting.path).getParent() + slash + group;
            File file = new File(path);

            String text;
            StringBuilder total = new StringBuilder();
            if (!file.canRead()) throw new FileFailException(FileFailMessage.doNotReadFile);
            if (!path.toLowerCase(Locale.ROOT).endsWith(".otl")) throw new FileFailException(FileFailMessage.notMatchExtension);
            try (BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
                while ((text = reader.readLine()) != null) total.append(text).append("\n");
                String t = Setting.bracket.bracket(total.toString());
                Arrays.stream(t.split("\\n")).forEach(Setting::start);
            } catch (IOException ignored) {}
        }
        // path../test/hi.otl
//        File file = new File(Setting.path + ".." + slash + "test" + slash + "hi.otl");


    }
}
