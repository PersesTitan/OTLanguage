package system.start;

import system.work.FinishWork;

import java.util.regex.Pattern;

public class Finish implements FinishWork {
    private final Pattern pattern;

    public Finish(String patternText) {
        this.pattern = Pattern.compile("^\\s*" + patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start() {
        System.exit(0);
    }
}
