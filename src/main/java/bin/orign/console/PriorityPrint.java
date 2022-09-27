package bin.orign.console;

import bin.token.ConsoleToken;
import bin.token.Token;
import work.StartWork;

import java.util.Map;
import java.util.regex.Pattern;

public class PriorityPrint implements
        StartWork, ConsoleToken, Token {
    private final String patternText;
    private final Pattern pattern;

    public PriorityPrint(String type) {
        this.patternText = startMerge(type, orMerge(BLANKS, END));
        this.pattern = Pattern.compile(this.patternText);
    }

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
        System.out.print(origen.replaceFirst(this.patternText, ""));
    }
}
