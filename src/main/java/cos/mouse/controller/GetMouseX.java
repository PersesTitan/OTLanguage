package cos.mouse.controller;

import bin.token.LoopToken;
import work.ReturnWork;

import java.awt.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMouseX implements ReturnWork, LoopToken {
    private final String group;
    private final Pattern pattern;
    private Matcher matcher;

    public GetMouseX(String className, String type) {
        this.group = Pattern.quote(className + ACCESS + type);
        String patternText = startEndMerge(VARIABLE_GET_S, className, ACCESS, type, VARIABLE_GET_E);
        this.pattern = Pattern.compile(patternText);
    }

    @Override
    public boolean check(String line) {
        this.matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String x = String.valueOf(MouseInfo.getPointerInfo().getLocation().x);
            line = line.replaceFirst(group, x);
        }
        return line;
    }

    @Override
    public ReturnWork first() {
        return this;
    }
}
