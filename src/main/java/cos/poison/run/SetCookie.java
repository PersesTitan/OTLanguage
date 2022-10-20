package cos.poison.run;

import bin.exception.VariableException;
import bin.token.LoopToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.work.PoisonStartWork;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetCookie implements RootWork, LoopToken, PoisonStartWork {
    private final int length;
    private final Matcher matcher;

    // ㄱㅋㄱ[][] ~ [][][][]
    // key, value, path, time
    public SetCookie(String type) {
        this.length = type.replace("\\", "").length();
        String patternText = startEndMerge(type, ARGUMENT.repeat(2), "(", ARGUMENT, "){0,2}");
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      HttpExchange exchange, Headers responseHeader,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        String[] tokens = bothEndCut(line.strip(), length + 1, 1).split(BR + BL, 4);
        switch (tokens.length) {
            case 2 -> setCookie(responseHeader, tokens[0], tokens[1], null, -1);
            case 3 -> {
                if (Pattern.matches("[0-9]+", tokens[2]))
                    setCookie(responseHeader, tokens[0], tokens[1], null, Integer.parseInt(tokens[2]));
                else setCookie(responseHeader, tokens[0], tokens[1], tokens[2], -1);
            }
            case 4 -> {
                if (Pattern.matches("[0-9]+", tokens[3]))
                    setCookie(responseHeader, tokens[0], tokens[1], tokens[2], Integer.parseInt(tokens[3]));
                else throw VariableException.typeMatch();
            }
        }
    }
}
