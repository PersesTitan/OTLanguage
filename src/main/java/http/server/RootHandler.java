package http.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http.items.Color;
import http.items.Temporary;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

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

                //[2022-01-01 01:01:12][GET] [PATH] /test | [QueryString] username=Test
                String dateFormat = "yyyy-MM-dd H:mm:ss";
                String query = exchange.getRequestURI().getQuery();
                System.out.printf("[%s]", new SimpleDateFormat(dateFormat).format(new Date()));
                System.out.printf("%s[%s]%s", Color.cyan, exchange.getRequestMethod(), Color.exit);
                System.out.printf(" %s[PATH]%s %s ", Color.blue, Color.exit, path == null ? "" : path);
                System.out.printf("| %s[QueryString]%s %s\n", Color.blue, Color.exit, query == null ? "" : query);

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
