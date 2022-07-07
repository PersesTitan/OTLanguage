package print;

import item.work.PrintWork;

import java.util.regex.Pattern;

public class PriorityPrintln implements PrintWork {
    private final String PATTERN = "^\\s*!ㅆㅁㅆ!(\\s|$)";
    private final Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public void start(String line) {
        System.out.println(line.replaceFirst(PATTERN, ""));
    }

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }
}
