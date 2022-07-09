package http.handler;

import com.sun.net.httpserver.HttpExchange;
import http.items.Temporary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpPostHandler {
    public String handle(HttpExchange exchange) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        UriParser uriParse = new UriParser();
        uriParse.parsesQuery(query, parameters);
        Temporary.POST.putAll(parameters);

        StringBuilder response = new StringBuilder();
        parameters.forEach((key, value) -> response.append(key).append("=").append(value).append("&"));
        int responseSize = response.toString().length();
        return responseSize<=0 ? "" : response.substring(0, responseSize-1).strip();
    }
}
