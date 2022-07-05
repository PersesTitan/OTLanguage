package math;

import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PRandom implements MathWork {
    private final Random rand = new Random();
    //ㅅㅎ@ㅎㅅ
    private final Pattern pattern = Pattern.compile("(^|\\s+)ㅅㅎ@ㅎㅅ($|\\s+)");
    //ㅅㅎ[숫자]@[숫자]ㅎㅅ
    private final Pattern rangePattern = Pattern.compile("(^|\\s+)ㅅㅎ\\d+@\\d+ㅎㅅ($|\\s+)");

    @Override
    public String start(String line) {
        Matcher matcher = pattern.matcher(line);
        Matcher rangeMatcher = rangePattern.matcher(line);
        while (matcher.find())
            line = line.replaceFirst("ㅅㅎ@ㅎㅅ", Integer.toString(rand.nextInt()));
        while (rangeMatcher.find()) {
            //ㅅㅎ[숫자]@[숫자]ㅎㅅ
            String value = rangeMatcher.group();
            StringTokenizer tokenizer = new StringTokenizer(value, "@");
            String number1 = tokenizer.nextToken();
            String number2 = tokenizer.nextToken();
            int num1 = Integer.parseInt(number1.replaceAll("[^0-9]", ""));
            int num2 = Integer.parseInt(number2.replaceAll("[^0-9]", ""));
            int randNumber = rand.nextInt(num1 + num2 + 1) + num1;
            line = line.replace(value, Integer.toString(randNumber));
        }
        return line;
    }

    @Override
    public boolean check(String line) {
        boolean bool = pattern.matcher(line).find();
        return bool || rangePattern.matcher(line).find();
    }
}
