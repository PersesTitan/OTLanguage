package http.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http.define.HttpMethodType;
import http.handler.HttpGetHandler;
import http.handler.HttpPostHandler;
import http.items.HttpRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class RootHandler implements HttpHandler, HttpRepository {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        try (exchange; OutputStream responsive = exchange.getResponseBody()) {
            if (HttpRepository.pathMap.containsKey(path)) {
                String filePath = new CreateHTML().changeVariable(HttpRepository.pathMap.get(path));
                ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(filePath);

                int contentLen = byteBuffer.limit();
                byte[] content = new byte[contentLen];
                byteBuffer.get(content, 0, contentLen);

                Headers headers = exchange.getResponseHeaders();
                headers.add("Content-Type", "text/html;charset=UTF-8");
                headers.add("Content-Length", String.valueOf(contentLen));

                HttpMethodType method = exchange.getRequestMethod().equals("POST") ?
                        HttpMethodType.POST : HttpMethodType.GET;
                String query = exchange.getRequestURI().getQuery();

                String value;
                if (method.equals(HttpMethodType.POST)) value = new HttpPostHandler().handle(exchange);
                else value = new HttpGetHandler().handle(exchange);

                //get, /, name=get
                printLog(method, path, value);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLen);
                responsive.write(content);

            } else {
                if (!path.equals("/favicon.ico")) System.out.println(path + "가 정의되어 있지 않습니다.");
            }
        }
    }
}
