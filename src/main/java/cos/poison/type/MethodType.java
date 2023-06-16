package cos.poison.type;

import bin.token.EditToken;
import bin.token.SepToken;
import bin.token.check.CheckToken;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.PoisonException;
import cos.poison.PoisonToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.function.BiFunction;

public enum MethodType {
    // 민트색
    POST    ("\033[30m\033[102m", MethodType::handlerPost),
    // 초록색
    GET     ("\033[30m\033[106m", MethodType::handlerGet),
    // 노란색
    PUT     ("\033[30m\033[103m", MethodType::handlerGet),
    // 파란색
    PATCH   ("\033[30m\033[104m", MethodType::handlerGet),
    // 빨간색
    DELETE  ("\033[30m\033[101m", MethodType::handlerGet)
    ;

    private final String print;
    private final BiFunction<HttpExchange, DataType, Map<String, Object>> function;

    MethodType(String color, BiFunction<HttpExchange, DataType, Map<String, Object>> function) {
        this.print = String.format("%s %s %s\033[0m", color, name(), " ".repeat(7-name().length()));
        this.function = function;
    }

    public void print(String path, String query) {
        PoisonToken.printTime();
        System.out.printf("%s [경로] \033[1;34m %s \033[0m | [값] \033[1;34m %s \033[0m%s",
                this.print,
                path == null ? "" : path,
                query == null ? "" : query,
                SepToken.LINE);
    }

    public Map<String, Object> getParams(HttpExchange exchange, DataType dataType) {
        return function.apply(exchange, dataType);
    }

    public static MethodType get(String method) {
        try {
            return MethodType.valueOf(method.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw PoisonException.METHOD_TYPE_ERROR.getThrow(method);
        }
    }

    // data control
    private static Map<String, Object> handlerPost(HttpExchange exchange, DataType dataType) {
        Map<String, Object> parameter = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            StringBuilder queryBuilder = new StringBuilder();
            reader.lines().forEach(queryBuilder::append);
            String query = queryBuilder.toString();
            parsesQuery(dataType, query, parameter);
        } catch (IOException e) {
            throw PoisonException.DO_NOT_READ.getThrow(null);
        }
        return parameter;
    }

    private static Map<String, Object> handlerGet(HttpExchange exchange, DataType dataType) {
        Map<String, Object> parameters = new HashMap<>();
        String query = exchange.getRequestURI().getRawQuery();
        try {
            parsesQuery(dataType, query, parameters);
        } catch (UnsupportedEncodingException e) {
            throw PoisonException.DO_NOT_READ.getThrow(null);
        }
        return parameters;
    }

    private static void parsesQuery(DataType dataType, String query, Map<String, Object> parameter)
            throws UnsupportedEncodingException {
        if (query == null) return;
        switch (dataType) {
            case JSON -> {
                if (CheckToken.bothCheck(query, '{', '}')) {
                    StringTokenizer tokenizer = new StringTokenizer(EditToken.bothCut(query).strip(), ",");
                    while (tokenizer.hasMoreTokens()) {
                        String[] tokens = EditToken.split(tokenizer.nextToken(), ':');
                        String key = tokens[0].strip();
                        String value = tokens[1].strip();
                        parameter.put(
                                CheckToken.bothCheck(key, '"', '"') ? EditToken.bothCut(key) : key,
                                CheckToken.bothCheck(value, '"', '"') ? EditToken.bothCut(value) : value
                        );
                    }
                } else throw PoisonException.DATA_PATTERN_ERROR.getThrow(dataType);
            }
            case URL -> {
                final String encoding = System.getProperty("file.encoding");
                StringTokenizer tokenizer = new StringTokenizer(query, "&");
                while (tokenizer.hasMoreTokens()) {
                    StringTokenizer paramTokenizer = new StringTokenizer(tokenizer.nextToken(), "=");
                    String key = paramTokenizer.hasMoreTokens()
                            ? URLDecoder.decode(paramTokenizer.nextToken(), encoding) : null;
                    String value = paramTokenizer.hasMoreTokens()
                            ? URLDecoder.decode(paramTokenizer.nextToken(), encoding) : "";
                    if (parameter.containsKey(key)) {
                        Object obj = parameter.get(key);
                        if (obj instanceof List<?>) return;
                        else if (obj instanceof String str) parameter.put(key, new ArrayList<>(List.of(str, value)));
                    } else parameter.put(key, value);
                }
            }
        }
    }
}
