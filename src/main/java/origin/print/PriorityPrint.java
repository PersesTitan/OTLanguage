package origin.print;

import origin.item.work.PrintWork;

import java.util.regex.Pattern;

public class PriorityPrint implements PrintWork {
    private final String PATTERN = "^\\s*!ㅅㅁㅅ!(\\s|$)";
    private final Pattern pattern = Pattern.compile(PATTERN);

    @Override
    public void start(String line) {
        System.out.print(line.replaceFirst(PATTERN, ""));
    }

    @Override
    public boolean check(String line) throws Exception {
        return pattern.matcher(line).find();
    }
}
