package system.start.exception;

import system.work.ThreadWork;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Sleep implements ThreadWork {
    private final Pattern pattern;

    //ㅡ_ㅡ 100.0
    public Sleep(String patternText) {
        this.pattern = Pattern.compile("^\\s*" + patternText + "\\s+\\d+\\s*$");
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        tokenizer.nextToken();
        long count = Long.parseLong(tokenizer.nextToken());

        try {
            Thread.sleep(count);
        } catch (InterruptedException ignored) {}
    }
}
