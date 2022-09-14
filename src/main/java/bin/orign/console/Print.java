package bin.orign.console;

import bin.token.ConsoleToken;
import bin.token.Token;
import work.StartWork;

import java.util.Map;
import java.util.regex.Pattern;

public class Print implements
        StartWork, ConsoleToken, Token {
    private final String patternText;
    private final Pattern pattern;

    public Print(String type) {
        this.patternText = startMerge(type, orMerge(BLANKS, END));
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
        System.out.print(line.replaceFirst(this.patternText, ""));
    }
}
