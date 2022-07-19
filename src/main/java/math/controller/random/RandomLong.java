package math.controller.random;

import math.model.RandomWork;
import origin.exception.RandomException;
import origin.exception.RandomMessage;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomLong implements RandomWork {
    private final Random random = new Random();
    private final String patternText = "\\d+\\s*@ㅉ@\\s*\\d+|@ㅉ@";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String text = matcher.group();
            if (Pattern.compile("\\d").matcher(text).find()) {
                String[] values = text.split("@ㅉ@");
                long num1 = Long.parseLong(values[0].strip());
                long num2 = Long.parseLong(values[1].strip());
                if (num1 == num2) throw new RandomException(RandomMessage.randomSame);
                line = line.replaceFirst(patternText, Long.toString(random.nextLong(num1, num2)));
            } else line = line.replaceFirst(patternText, Long.toString(random.nextLong()));
        }
        return line;
    }
}
