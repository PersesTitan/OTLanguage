package input.conroller.mouse.controller;

import input.conroller.mouse.define.MousePositionWork;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MousePositionX implements MousePositionWork {
    private final String patternText; // :ㅁㅇㅁ~ㄱㄹㄱ(_ )
    private final Pattern pattern;

    public MousePositionX(String className, String patternText) {
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
            var positionX = MouseInfo.getPointerInfo().getLocation().x;
            var positionY = MouseInfo.getPointerInfo().getLocation().y;
            String builder = "[" + positionX + ", " + positionY + "]";
            line = line.replaceFirst(this.patternText, builder);
        }
        return line;
    }
}
