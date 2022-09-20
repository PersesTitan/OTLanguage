package cos.poison.handler;

import bin.exception.ServerException;
import com.sun.net.httpserver.HttpExchange;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static cos.poison.controller.HttpServerManager.uriParser;

public class HttpGetHandler implements HttpHandlerInf {
    @Override
    public HandlerDao handle(HttpExchange exchange) {
        Map<String, Object> parameters = new HashMap<>();
        try {
            URI requestUri = exchange.getRequestURI();
            String query = requestUri.getRawQuery();
            uriParser.parsesQuery(query, parameters);

            String path = exchange.getRequestURI().getPath();
            StringBuilder response = new StringBuilder();
            parameters.forEach((key, value) -> response.append(key).append("=").append(value).append(" "));
            return new HandlerDao(response.toString(), path, parameters);
        } catch (UnsupportedEncodingException e) {
            throw ServerException.fileReadError();
        }
    }
}
