package math.controller;

import math.model.CalculationWork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculation implements CalculationWork {
    private final String patternText = "\\[[\\s\\S]+]ㅇ=ㅇ\\[[\\s\\S]+]";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            //[값1]ㅇ=ㅇ[값2]
            String[] values = matcher.group().split("ㅇ=ㅇ");
            String value1 = values[0].substring(1, values[0].length()-1);
            String value2 = values[1].substring(1, values[1].length()-1);
            line = line.replaceFirst(patternText, value1.equals(value2)?"ㅇㅇ":"ㄴㄴ");
        }
        return line;
    }
}
