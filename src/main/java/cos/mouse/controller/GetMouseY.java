package cos.mouse.controller;

import bin.token.LoopToken;
import work.ReturnWork;

import java.awt.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMouseY implements ReturnWork, LoopToken {
    private final String group;
    private final Pattern pattern;
    private Matcher matcher;

    public GetMouseY(String className, String type) {
        this.group = Pattern.quote(className + ACCESS + type);
        String patternText = startEndMerge(VARIABLE_GET_S, className, ACCESS, type, VARIABLE_GET_E);
        this.pattern = Pattern.compile(patternText);
    }

    @Override
    public boolean check(String line) {
        matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String y = String.valueOf(MouseInfo.getPointerInfo().getLocation().y);
            line = line.replaceFirst(group, y);
        }
        return line;
    }
}