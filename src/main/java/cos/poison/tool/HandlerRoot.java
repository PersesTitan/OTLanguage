package cos.poison.tool;

import bin.apply.mode.DebugMode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import cos.poison.PoisonException;
import cos.poison.PoisonItem;
import cos.poison.type.MethodType;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class HandlerRoot implements HttpHandler {
    private final PoisonItem poisonItem;

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        try (exchange; OutputStream responseBody = exchange.getResponseBody()) {
            AtomicInteger statusCode = new AtomicInteger(HttpURLConnection.HTTP_OK);
            AtomicReference<String> nowPath = new AtomicReference<>(path);

            MethodType methodMode = MethodType.get(exchange.getRequestMethod());

            this.poisonItem.setExchange(exchange);
            try {
                byte[] body = this.poisonItem.get(methodMode, path, exchange, statusCode);
                exchange.sendResponseHeaders(statusCode.get(),0);
                if (body != null) responseBody.write(body);
            } catch (Exception e) {
                if (DebugMode.isDevelopment()) e.printStackTrace();
            }
        } catch (IOException e) {
            if (DebugMode.isDevelopment()) e.printStackTrace();
            throw PoisonException.DO_NOT_READ.getThrow(null);
        } finally {
            this.poisonItem.setExchange(null);
        }
    }
}
