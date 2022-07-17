package math.controller.random;

import math.model.RandomWork;
import origin.exception.RandomException;
import origin.exception.RandomMessage;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//정수 램던 생성
public class RandomInteger implements RandomWork {
    private final Random random = new Random();
    private final String patternText = "\\d+\\s*@ㅈ@\\s*\\d+|@ㅈ@";
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
                String[] values = text.split("@ㅈ@");
                int num1 = Integer.parseInt(values[0].strip());
                int num2 = Integer.parseInt(values[1].strip());
                if (num1 == num2) throw new RandomException(RandomMessage.randomSame);
                line = line.replaceFirst(patternText, Integer.toString(random.nextInt(num1, num2)));
            } else line = line.replaceFirst(patternText, Integer.toString(random.nextInt()));
        }
        return line;
    }
}
