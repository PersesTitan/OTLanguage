package etc.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import cos.poison.root.RootWork;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class URLTest implements RootWork {
    public static void main(String[] args) throws IOException {
        new URLTest();
    }

    public URLTest() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);
        String newUrl ="/a";
        server.createContext("/", new Handler());
        server.createContext("/{id}/a", t -> new Handler());
        server.start();
    }

    class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println(exchange.getRequestURI().getRawPath());
            System.out.println(exchange.getRequestURI().getPath());
            String response = "This is the response";
//            Headers responseHeaders = t.getResponseHeaders();
//            responseHeaders.set("Location", newUrl);

            Headers headers = exchange.getResponseHeaders();

            setCookie(headers, "test1", "1", null, 20);
            setCookie(headers, "test2", "2", null, -1);
            exchange.sendResponseHeaders(200,0);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
