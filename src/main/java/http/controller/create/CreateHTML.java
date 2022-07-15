package http.controller.create;

import http.model.HttpCreateWork;
import pattern.MakePatternWork;

import java.util.regex.Pattern;

public abstract class CreateHTML implements MakePatternWork {
    private final Pattern pattern;

    public CreateHTML(String pattern) {
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
