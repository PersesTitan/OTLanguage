package cos.poison.run;

import bin.token.LoopToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.work.PoisonStartWork;
import work.ReturnWork;
import work.StartWork;

import java.net.HttpURLConnection;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Redirect implements RootWork, LoopToken, PoisonStartWork {
    private final Matcher matcher;
    public HttpExchange exchange;
    public Headers responseHeader;
    public AtomicInteger statCode;
    public AtomicReference<String> nowPath;


    public Redirect(String className, String type) {
        String patternText = startEndMerge(className, ACCESS, type, BLANKS, "\\S+");
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        StringTokenizer tokenizer = new StringTokenizer(line.strip());
        tokenizer.nextToken();
        statCode.set(HttpURLConnection.HTTP_MOVED_TEMP);
        redirect(responseHeader, tokenizer.nextToken());
    }

    @Override
    public void first() {

    }

    @Override
    public void setExchange(HttpExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void setResponseHeader(Headers responseHeader) {
        this.responseHeader = responseHeader;
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
