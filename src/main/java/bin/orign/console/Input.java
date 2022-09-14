package bin.orign.console;

import bin.token.ConsoleToken;
import bin.token.Token;
import bin.token.VariableToken;
import work.ReturnWork;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input implements
        ReturnWork, ConsoleToken, Token, VariableToken {
    private final Scanner scanner = new Scanner(System.in);
    private final String patternText;
    private final Pattern pattern;

    public Input(String type) {
        this.patternText = VARIABLE_GET_S + type + VARIABLE_GET_E;
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) line = line.replaceFirst(patternText, scanner.next());
        return line;
    }
}
