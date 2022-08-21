package system.start.keyword;

import system.work.SystemWork;

import java.util.regex.Pattern;

public abstract class SpecialKeyword implements SystemWork {
    private final Pattern pattern;

    public SpecialKeyword(String patternText) {
        this.pattern = Pattern.compile("^\\s*" + patternText + "\\s*$");
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public abstract void start(String line);
}
