package input.conroller.mouse.controller;

import input.conroller.mouse.define.MousePositionWork;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MousePositionXY implements MousePositionWork {
    private final String patternText; // :ㅁㅇㅁ~ㄱㄹㅅ(_ )
    private final Pattern pattern;

    public MousePositionXY(String className, String patternText) {
        this.patternText = ":" + className + "~" + patternText + "[ _]";
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            var position = MouseInfo.getPointerInfo().getLocation().x;
            line = line.replaceFirst(this.patternText, String.valueOf(position));
        }
        return line;
    }
}
