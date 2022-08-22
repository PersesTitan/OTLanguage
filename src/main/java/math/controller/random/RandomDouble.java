package math.controller.random;

import math.model.RandomWork;
import origin.exception.RandomException;
import origin.exception.RandomMessage;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomDouble implements RandomWork {
    private final Random random = new Random();
    private final String patternText;
    private final Pattern pattern;

    public RandomDouble(String patternText) {
        this.patternText = ":((\\d+\\.\\d+)\\s*"+patternText+"\\s*(\\d+\\.\\d+)|"+patternText+")[_ ]";
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
            String gr = matcher.group();
            gr = gr.substring(1, gr.length()-1);
            if (Pattern.compile("\\d").matcher(gr).find()) {
                var value = gr.split("@ㅆ@");
                double num1 = Double.parseDouble(value[0].strip());
                double num2 = Double.parseDouble(value[1].strip());
                if (num1 == num2) throw new RandomException(RandomMessage.randomSame);
                line = line.replaceFirst(patternText, Double.toString(random.nextDouble(num1, num2)));
            } else line = line.replaceFirst(patternText, Double.toString(random.nextDouble()));
        }
        return line;
    }
}
