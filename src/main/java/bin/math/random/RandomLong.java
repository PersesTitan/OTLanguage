package bin.math.random;

import bin.token.Token;
import bin.token.VariableToken;
import bin.token.cal.NumberToken;
import work.ReturnWork;

import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomLong implements
        ReturnWork, Token, VariableToken, NumberToken {
    private final Random random = new Random();
    private final String patternText;
    private final String type;
    private final Pattern pattern;

    public RandomLong(String type) {
        this.type = type;
        this.patternText = VARIABLE_GET_S + orMerge(INT + type + INT, type + INT, type) + VARIABLE_GET_E;
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line, Map<String, Map<String, Object>>[] repositoryArray) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String group = matcher.group();
            group = group.substring(1, group.length()-1);
            if (Pattern.compile(INT).matcher(group).find()) {
                String[] values = group.split(type);
                if (values[0].isBlank()) {
                    long num2 = Long.parseLong(values[1]);
                    line = line.replaceFirst(this.patternText, Long.toString(random.nextLong(num2)));
                } else {
                    long num1 = Long.parseLong(values[0]);
                    long num2 = Long.parseLong(values[1]);
                    line = line.replaceFirst(this.patternText, Long.toString(random.nextLong(num1, num2)));
                }
            } else line = line.replaceFirst(this.patternText, Long.toString(random.nextLong()));
        }
        return line;
    }
}
