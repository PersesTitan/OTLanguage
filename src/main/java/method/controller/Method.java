package method.controller;

import method.define.MethodWork;

import java.util.regex.Pattern;

public record Method(Pattern pattern) implements MethodWork {

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start() {

    }
}
