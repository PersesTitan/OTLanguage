package cos.poison.run;

import bin.token.LoopToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.work.PoisonReturnWork;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetCookie implements RootWork, LoopToken, PoisonReturnWork {
    private final Matcher matcher;

    public GetCookie(String type) {
        String patternText = VARIABLE_GET_S + type + BLANKS + VARIABLE_HTML + VARIABLE_GET_E;
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public String start(String line, String origen,
                        HttpExchange exchange, Headers requestHeader,
                        Map<String, Map<String, Object>>[] repositoryArray) {
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
}
