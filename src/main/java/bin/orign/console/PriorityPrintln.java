package bin.orign.console;

import bin.token.ConsoleToken;
import bin.token.Token;
import org.apache.xerces.impl.xpath.regex.Match;
import work.StartWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record PriorityPrintln(int type) implements StartWork, ConsoleToken, Token {

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
        System.out.println(origen.substring(type).stripIndent());
    }

    @Override
    public void first() {

    }
}
