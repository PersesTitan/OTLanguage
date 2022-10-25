package cos.poison.run.replace;

import bin.token.LoopToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.work.PoisonReturnWork;
import work.ReturnWork;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetCookie implements PoisonReturnWork, RootWork, LoopToken {
    private final Matcher matcher;
    public HttpExchange exchange;
    public Headers requestHeader;
    public AtomicInteger statCode;
    public AtomicReference<String> nowPath;

    public GetCookie(String className, String type) {
        String patternText = merge(VARIABLE_GET_S, className, ACCESS, type, BLANKS, VARIABLE_HTML, VARIABLE_GET_E);
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public ReturnWork first() {
        return this;
    }

    @Override
    public String start(String line, Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        if (matcher.find()) {
            String group = matcher.group();
            StringTokenizer tokenizer = new StringTokenizer(bothEndCut(group));
            tokenizer.nextToken();
            String value = getCookie(requestHeader, tokenizer.nextToken());
            if (value != null) line = line.replace(group, value);
        }
        return line;
    }

    @Override
    public void setExchange(HttpExchange exchange) {
        this.requestHeader = exchange.getRequestHeaders();
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
