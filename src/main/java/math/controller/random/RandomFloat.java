package math.controller.random;

import math.model.RandomWork;
import origin.exception.RandomException;
import origin.exception.RandomMessage;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomFloat implements RandomWork {
    private final Random random = new Random();
    private final String patternText = "\\d+\\.\\d+\\s*@ㅅ@\\s+\\d+\\.\\d+|@ㅅ@";
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String gr = matcher.group();
            if (Pattern.compile("\\d").matcher(gr).find()) {
                var value = gr.split("@ㅅ@");
                float num1 = Float.parseFloat(value[0].strip());
                float num2 = Float.parseFloat(value[1].strip());
                if (num1 == num2) throw new RandomException(RandomMessage.randomSame);
                line = line.replaceFirst(patternText, Float.toString(random.nextFloat(num1, num2)));
            } else line = line.replaceFirst(patternText, Float.toString(random.nextFloat()));
        }
        return line;
    }
}
