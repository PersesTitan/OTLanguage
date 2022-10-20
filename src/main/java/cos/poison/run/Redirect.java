package cos.poison.run;

import bin.token.LoopToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.work.PoisonStartWork;

import java.net.HttpURLConnection;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cos.poison.root.HandlerRoot.statusCode;

public class Redirect implements RootWork, LoopToken, PoisonStartWork {
    private final Matcher matcher;

    public Redirect(String type) {
        String patternText = startEndMerge(type, BLANKS, "\\S+");
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
        StringTokenizer tokenizer = new StringTokenizer(line.strip());
        tokenizer.nextToken();
        redirect(responseHeader, tokenizer.nextToken());
        statusCode.set(HttpURLConnection.HTTP_MOVED_TEMP);
    }
}
