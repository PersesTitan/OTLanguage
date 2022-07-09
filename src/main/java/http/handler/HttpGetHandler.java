package http.handler;

import com.sun.net.httpserver.HttpExchange;
import http.items.Temporary;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HttpGetHandler {

    public String handle(HttpExchange exchange) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        URI requestUri = exchange.getRequestURI();
        String query = requestUri.getRawQuery();
        UriParser uriParser = new UriParser();
        uriParser.parsesQuery(query, parameters);
        Temporary.GET.putAll(parameters);

        StringBuilder response = new StringBuilder();
        parameters.forEach((key, value) -> response.append(key).append("=").append(value).append("&"));
        int responseSize = response.toString().length();
        return responseSize<=0 ? "" : response.substring(0, responseSize-1).strip();
    }
}
