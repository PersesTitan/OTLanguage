package origin.variable.controller.list;

import origin.variable.model.VariableListWork;

import java.util.regex.Pattern;

public abstract class ListVariable implements VariableListWork {
    private final Pattern pattern;

    public ListVariable(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    public abstract String start(String line);
}
