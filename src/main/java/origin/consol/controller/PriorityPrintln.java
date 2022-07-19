package origin.consol.controller;

import origin.consol.define.PriorityPrintWork;

import java.util.regex.Pattern;

public class PriorityPrintln implements PriorityPrintWork {
    private final String PATTERN = "^\\s*!ㅆㅁㅆ!(\\s|$)";
    private final Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        System.out.println(line.replaceFirst(PATTERN, ""));
    }
}
