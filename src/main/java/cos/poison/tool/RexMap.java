package cos.poison.tool;

import bin.token.SepToken;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.PoisonException;
import cos.poison.type.URLType;

import java.net.HttpURLConnection;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RexMap extends HashMap<Matcher, BiFunction<HttpExchange, AtomicInteger, Object>> {
    public RexMap(Matcher matcher, BiFunction<HttpExchange, AtomicInteger, Object> function) {
        super.put(matcher, function);
    }

    public byte[] start(String path, HttpExchange exchange, AtomicInteger status) {
        try {
            for (Entry<Matcher, BiFunction<HttpExchange, AtomicInteger, Object>> entry : super.entrySet())
                if (entry.getKey().reset(path).find()) return (byte[]) entry.getValue().apply(exchange, status);
            status.set(HttpURLConnection.HTTP_NOT_FOUND);
            return null;
        } catch (PatternSyntaxException e) {
            throw PoisonException.URL_REGEXP_ERROR.getThrow(
                    new StringBuilder(SepToken.LINE).append('\t')
                            .append(e.getPattern(), 3, e.getPattern().lastIndexOf('>'))
                            .append(SepToken.LINE).append('\t')
                            .append(" ".repeat(e.getIndex()-3)).append('^')
                            .append(SepToken.LINE).append('\t')
            );
        }
    }

    private static final Matcher matcher = Pattern.compile("(?<=/|^):([A-Za-z][A-Za-z0-9]*)(?=/|$)").matcher("");
    public static Matcher makeMatcher(String path, Map<String, String> varMap) {
        Matcher matcher = Pattern.compile('^' + RexMap.matcher.reset(path).replaceAll(m -> {
            String group = m.group().substring(1);
            if (varMap.containsKey(group)) return "(?<$1>" + URLType.getText(varMap.remove(group)) + ')';
            return m.group();
        }) + '$').matcher("");
        if (!varMap.isEmpty()) throw PoisonException.URL_USE_ERROR.getThrow(varMap.keySet());
        return matcher;
    }
}
