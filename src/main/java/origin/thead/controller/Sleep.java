package origin.thead.controller;

import origin.thead.model.ThreadWork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sleep implements ThreadWork {
    private final String patternText = "^\\s*ㅡ_ㅡ\\s*\\d+";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
        Matcher matcher = pattern.matcher(line);

    }
}
