package bin.orign.console;

import bin.token.ConsoleToken;
import bin.token.Token;
import work.StartWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriorityPrintTap implements
        StartWork, ConsoleToken, Token {
    private final String patternText;
    private final Matcher matcher;

    public PriorityPrintTap(String type) {
        this.patternText = startMerge(type, orMerge(BLANKS, END));
        this.matcher = Pattern.compile(this.patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
        System.out.print(origen.replaceFirst(this.patternText, "") + "\t");
    }
}