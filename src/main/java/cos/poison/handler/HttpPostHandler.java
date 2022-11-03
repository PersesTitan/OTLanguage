package cos.poison.handler;

import bin.exception.ServerException;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static cos.poison.controller.HttpServerManager.uriParser;
import static java.nio.charset.StandardCharsets.*;

public class HttpPostHandler implements HttpHandlerInf {
    public HandlerDao handle(HttpExchange exchange) {
        try (BufferedReader br =
                     new BufferedReader(
                     new InputStreamReader(exchange.getRequestBody(), UTF_8))) {
            String path = exchange.getRequestURI().getPath();

            Map<String, Object> parameters = new HashMap<>();
            StringBuilder query = new StringBuilder();
            br.lines().forEach(query::append);
            uriParser.parsesQuery(query.toString(), parameters);

            return new HandlerDao(parameters.isEmpty() ? "" : parameters.toString(), path, parameters);
        } catch (IOException ignored) {
            throw new ServerException().fileReadError();
        }
    }
}
