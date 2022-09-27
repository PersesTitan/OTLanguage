package bin.math.random;

import bin.token.Token;
import bin.token.VariableToken;
import bin.token.cal.NumberToken;
import work.ReturnWork;

import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomFloat implements
        ReturnWork, Token, VariableToken, NumberToken {
    private final Random random = new Random();
    private final String patternText;
    private final String type;
    private final Pattern pattern;

    public RandomFloat(String type) {
        this.type = type;
        this.patternText = VARIABLE_GET_S + orMerge(FLOAT + type + FLOAT, type + FLOAT, type) + VARIABLE_GET_E;
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line, Map<String, Map<String, Object>>[] repositoryArray) {
        Matcher matcher = Pattern.compile(this.patternText).matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            group = group.substring(1, group.length()-1);
            if (group.matches(FLOAT)) {
                String[] values = group.split(type);
                if (values[0].isBlank()) {
                    float num2 = Float.parseFloat(values[1]);
                    line = line.replaceFirst(patternText, Float.toString(random.nextFloat(num2)));
                } else {
                    float num1 = Float.parseFloat(values[0]);
                    float num2 = Float.parseFloat(values[1]);
                    line = line.replaceFirst(patternText, Float.toString(random.nextFloat(num1, num2)));
                }
            } line = line.replaceFirst(patternText, Float.toString(random.nextFloat()));
        }
        return line;
    }
}
