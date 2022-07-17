package http.handler;

import com.sun.net.httpserver.HttpExchange;
import http.items.HttpRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpPostHandler {
    public String handle(HttpExchange exchange) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        var isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        var br = new BufferedReader(isr);
        var query = br.readLine();
        var uriParse = new UriParser();
        uriParse.parsesQuery(query, parameters);
        //경로를 키 값으로 저장
        String path = exchange.getRequestURI().getPath();
        HttpRepository.POST.get(path).putAll(parameters);

        var response = new StringBuilder();
        parameters.forEach((key, value) -> response.append(key).append("=").append(value).append(" "));
        return response.toString();
    }
}
