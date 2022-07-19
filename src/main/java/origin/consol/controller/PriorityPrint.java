package origin.consol.controller;

import origin.consol.define.PriorityPrintWork;

import java.util.regex.Pattern;

public class PriorityPrint implements PriorityPrintWork {
    private final String PATTERN = "^\\s*!ㅅㅁㅅ!(\\s|$)";
    private final Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public void start(String line) {
        System.out.print(line.replaceFirst(PATTERN, ""));
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }
}
