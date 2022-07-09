package http.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http.handler.HttpGetHandler;
import http.handler.HttpPostHandler;
import http.items.Temporary;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class RootHandler implements HttpHandler, Temporary {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        try (OutputStream responsive = exchange.getResponseBody()) {
            if (Temporary.pathMap.containsKey(path)) {
                String filePath = new CreateHTML().changeVariable(Temporary.pathMap.get(path));
                ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(filePath);

                int contentLen = byteBuffer.limit();
                byte[] content = new byte[contentLen];
                byteBuffer.get(content, 0, contentLen);

                Headers headers = exchange.getResponseHeaders();
                headers.add("Content-Type", "text/html;charset=UTF-8");
                headers.add("Content-Length", String.valueOf(contentLen));

                String method = exchange.getRequestMethod();
                String query = exchange.getRequestURI().getQuery();

                String value;
                if (method.equals("POST")) value = new HttpPostHandler().handle(exchange);
                else value = new HttpGetHandler().handle(exchange);

                printLog(method, path, value);

                exchange.sendResponseHeaders(200, contentLen);
                responsive.write(content);


            } else {
                if (!path.equals("/favicon.ico")) System.out.println(path + "가 정의되어 있지 않습니다.");
            }
        } finally {
            exchange.close();
        }
    }
}
