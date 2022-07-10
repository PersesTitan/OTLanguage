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
        var requestUri = exchange.getRequestURI();
        var query = requestUri.getRawQuery();
        var uriParser = new UriParser();
        uriParser.parsesQuery(query, parameters);
        Temporary.GET.putAll(parameters);

        var response = new StringBuilder();
        parameters.forEach((key, value) -> response.append(key).append("=").append(value).append(" "));
        return response.toString();
    }
}
