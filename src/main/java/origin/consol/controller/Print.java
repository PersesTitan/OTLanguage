package origin.consol.controller;

import origin.consol.define.PrintWork;

import java.util.regex.Pattern;

public class Print implements PrintWork {
    private final String patternText;
    private final Pattern pattern;

    public Print(String patternText) {
        this.patternText = "^\\s*"+patternText+"(\\s|$)";
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        //ㅅㅁㅅ 출력될 값 => 출력될 값
        System.out.print(line.replaceFirst(patternText, ""));
    }
}
