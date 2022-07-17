package http.handler;

import com.sun.net.httpserver.HttpExchange;
import http.items.HttpRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpGetHandler {

    public String handle(HttpExchange exchange) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        var requestUri = exchange.getRequestURI();
        var query = requestUri.getRawQuery();
        var uriParser = new UriParser();
        uriParser.parsesQuery(query, parameters);
        //경로를 키 값으로 저장
        String path = exchange.getRequestURI().getPath();
        HttpRepository.GET.get(path).putAll(parameters);

        var response = new StringBuilder();
        parameters.forEach((key, value) -> response.append(key).append("=").append(value).append(" "));
        return response.toString();
    }
}