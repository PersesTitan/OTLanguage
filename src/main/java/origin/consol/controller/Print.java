package origin.consol.controller;

import origin.consol.define.PrintWork;

import java.util.regex.Pattern;

public class Print implements PrintWork {
    private final String patternText = "^\\s*ㅅㅁㅅ($|\\s)";
    private final Pattern pattern = Pattern.compile(patternText);

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
