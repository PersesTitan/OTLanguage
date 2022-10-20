package bin.orign.console;

import bin.token.ConsoleToken;
import bin.token.Token;
import work.StartWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record PriorityPrintTap(int type) implements
        StartWork, ConsoleToken, Token {

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
        System.out.print(origen.substring(type).stripIndent() + "\t");
    }
}