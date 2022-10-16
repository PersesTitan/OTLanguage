package http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import cos.http.controller.HttpMethod;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static bin.token.VariableToken.VARIABLE_HTML;
import static bin.token.VariableToken.VARIABLE_PUT;

public class HandlerRootTest {
    private final Map<HttpMethod, Map<String, String>> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        new HandlerRootTest();
    }

    HandlerRootTest() throws IOException {
        for (HttpMethod method : HttpMethod.values()) map.put(method, new HashMap<>());
        puts(HttpMethod.GET, "/", "1");
        puts(HttpMethod.GET, "/:a", "2");
        puts(HttpMethod.GET, "/a/:b", "3");

        System.out.println(map);
        HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);
        server.createContext("/", new HandlerRoot());
        server.start();
//        path.replaceAll(":" + VARIABLE_HTML, "[0-9]+")
    }

    private void puts(HttpMethod method, String path, String value) {
        String type = "[0-9]+";
        String patternText = String.format("(?<$0>%s)", type);
        path = (path.endsWith("/") ? path : path + "/")
                .replaceAll(VARIABLE_PUT + VARIABLE_HTML, patternText)
                .replace("<:", "<");
        map.get(method).put(path, value);
    }

    private class HandlerRoot implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            HttpMethod method = HttpMethod.valueOf(exchange.getRequestMethod());
            String path = exchange.getRequestURI().getPath();
            path = path.endsWith("/") ? path : path + "/";

            Headers responseHeader = exchange.getResponseHeaders();
            Headers requestHeader = exchange.getRequestHeaders();
            var methodRepository = map.get(method);
            if (methodRepository.containsKey(path)) {
                System.out.println(methodRepository.get(path));
            } else {
                for (Map.Entry<String, String> entry : methodRepository.entrySet()) {
                    if (path.matches(entry.getKey())) {
                        System.out.println(entry.getValue());
                        return;
                    }
                }
                System.out.println("pass");

                exchange.sendResponseHeaders(200,0);
//                headers.add("Content-Type", "text/html;charset=UTF-8");
                OutputStream os = exchange.getResponseBody();
                os.write(Files.readString(Path.of("index.html")).getBytes());
                os.close();
            }
        }
    }

    private record HandlerItem() { }
}
