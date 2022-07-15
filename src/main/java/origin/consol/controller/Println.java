package origin.consol.controller;

import origin.consol.define.PrintWork;

import java.util.regex.Pattern;

public class Println implements PrintWork {
    private final String patternText = "(\\n|^)\\s*ㅆㅁㅆ($|\\s)";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        System.out.println(line.replaceFirst(patternText, ""));
    }
}
