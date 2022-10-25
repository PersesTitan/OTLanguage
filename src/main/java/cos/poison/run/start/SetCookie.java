package cos.poison.run.start;

import bin.exception.VariableException;
import bin.token.LoopToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.work.PoisonStartWork;
import work.StartWork;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetCookie implements RootWork, LoopToken, PoisonStartWork {
    private final int length;
    private final Matcher matcher;
    public HttpExchange exchange;
    public Headers responseHeader;
    public AtomicInteger statCode;
    public AtomicReference<String> nowPath;


    // ㄱㅋㄱ[][] ~ [][][][]
    // key, value, path, time
    public SetCookie(String className, String type) {
        String start = merge(className, ACCESS, type);
        this.length = start.replace("\\", "").length();
        String patternText = startEndMerge(start, ARGUMENT.repeat(2), "(", ARGUMENT, "){0,2}");
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray) {
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

    @Override
    public void first() {

    }

    @Override
    public void setExchange(HttpExchange exchange) {
        this.responseHeader = exchange.getResponseHeaders();
        this.exchange = exchange;
    }

    @Override
    public void setStatCode(AtomicInteger statCode) {
        this.statCode = statCode;
    }

    @Override
    public void setNowPath(AtomicReference<String> nowPath) {
        this.nowPath = nowPath;
    }
}
