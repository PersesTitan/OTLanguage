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

public class HttpPostHandler implements HttpHandlerInf {
    public HandlerDao handle(HttpExchange exchange) {
        var isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        try (var br = new BufferedReader(isr)) {
            Map<String, Object> parameters = new HashMap<>();

            String path = exchange.getRequestURI().getPath();
            String query = br.readLine();
            uriParser.parsesQuery(query, parameters);

            return new HandlerDao(
                    parameters.isEmpty() ? "" : parameters.toString(), path, parameters);
        } catch (IOException ignored) {
            throw ServerException.fileReadError();
        }
    }
}
