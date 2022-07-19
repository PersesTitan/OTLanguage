package pattern;

import java.util.regex.Pattern;

public abstract class MakePattern implements MakePatternWork {
    private final Pattern pattern;

    public MakePattern(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public abstract void start(String line);
}
