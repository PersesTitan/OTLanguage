package http.server;

import com.sun.net.httpserver.HttpServer;
import http.items.Color;
import http.items.HttpRepository;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class HttpServerManager implements HttpRepository {
    private static String DEF_HOST = "localhost";
    private static final int DEF_PORT = 9090;
    private HttpServer server;

    public HttpServerManager() {createServer(DEF_HOST, DEF_PORT);}
    public HttpServerManager(int port) {createServer(DEF_HOST, port);}

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    public void setHost(String host) {
        DEF_HOST = host;
    }

    //서버 생성
    private void createServer(String host, int port) {
        System.out.printf("URL http://%s:%d/%n", host, port);
        try {
            server = HttpServer.create(new InetSocketAddress(host, port), 0);
            HttpRepository.pathMap.forEach((k, v) -> {
                server.createContext(k, new RootHandler());
                POST.put(k, new HashMap<>());
                GET.put(k, new HashMap<>());
            });
        } catch (IOException e) {
            System.out.printf("%s서버 생성에 실패하였습니다.%s\n", Color.RED, Color.RESET);
        }
    }
}
