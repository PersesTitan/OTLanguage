package math.controller.random;

import math.model.RandomWork;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomBoolean implements RandomWork {
    private final Random random = new Random();
    private final String patternText = "\\d+\\s*@ㅂ@\\s*\\d+|@ㅂ@";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find())
            line = line.replaceFirst(patternText, random.nextBoolean() ? "ㅇㅇ" : "ㄴㄴ");
        return line;
    }
}
