package cos.http.controller;

import bin.apply.sys.item.Color;
import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;

public record HttpServerManager(String host, int port) implements HttpRepository {
    private static HttpServer httpServer;

    public void start() {
        if (httpServer != null) {

            httpServer.start();
        } else System.out.printf("%s서버가 존재하지 않습니다.%s\n", Color.RED, Color.RESET);
    }

    private void createServer() {
        System.out.printf("URL http://%s:%d/%n", host, port);
        try {
            httpServer = HttpServer.create(new InetSocketAddress(host, port), 0);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> httpServer.stop(0)));
        } catch (IOException e) {
            System.out.printf("%s서버 생성에 실패하였습니다.%s\n", Color.RED, Color.RESET);
        }
    }
}
