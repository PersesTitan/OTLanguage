package origin.variable.json.controller;

import origin.variable.json.define.MakeJsonWork;
import origin.variable.model.Repository;

import java.util.Stack;
import java.util.regex.Pattern;

public class MakeJson implements MakeJsonWork {
    private final String patternText;
    private final Pattern pattern;

    public MakeJson(String patternText) {
        this.patternText = "^\\s*" + patternText;
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line) {
    }

    private void makeJson(String line) {
        Stack<String> stack = new Stack<>();
        for (int i = 0; i<line.length(); i++) {
            char c = line.charAt(i);

        }
    }

    private void makeMap(String line) {

    }
}
