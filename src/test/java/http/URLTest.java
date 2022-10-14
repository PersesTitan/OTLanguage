package http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import cos.poison.root.RootWork;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class URLTest {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);
        String newUrl ="/a";
        server.createContext("/", t -> {
            String response = "This is the response";
//            Headers responseHeaders = t.getResponseHeaders();
//            responseHeaders.set("Location", newUrl);

            RootWork rootWork = new RootWork();
            Headers headers = t.getResponseHeaders();
            
            rootWork.setCookie(headers, "test1", "1", null, 20);
            rootWork.setCookie(headers, "test2", "2", null, -1);

            System.out.println(rootWork.getCookie(t.getRequestHeaders(), "test1"));
            System.out.println(rootWork.getCookie(t.getRequestHeaders(), "test2"));
            System.out.println(rootWork.getCookie(t.getRequestHeaders(), "test3"));

            t.sendResponseHeaders(200,0);

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.start();
    }

}
