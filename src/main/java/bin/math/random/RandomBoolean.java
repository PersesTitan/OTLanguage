package bin.math.random;

import bin.token.Token;
import bin.token.VariableToken;
import bin.token.cal.NumberToken;
import work.ReturnWork;

import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomBoolean implements
        ReturnWork, Token, VariableToken, NumberToken {
    private final Random random = new Random();
    private final Pattern pattern;
    private final String patternText;

    public RandomBoolean(String type) {
        this.patternText = VARIABLE_GET_S + type + VARIABLE_GET_E;
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line, Map<String, Map<String, Object>>[] repositoryArray) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find())
            line = line.replaceFirst(patternText, random.nextBoolean() ? "ㅇㅇ" : "ㄴㄴ");
        return line;
    }
}
