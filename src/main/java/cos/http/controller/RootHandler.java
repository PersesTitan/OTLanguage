package cos.http.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class RootHandler implements HttpHandler, HttpRepository {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        try (OutputStream responsive = exchange.getResponseBody()) {
//            String filePath = new CreateHTML().changeVariable(httpRepository.get(path), );
//            ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(filePath);

//            int contentLen = byteBuffer.limit();
//            byte[] content = new byte[contentLen];
//            byteBuffer.get(content, 0, contentLen);

        }
    }
}
