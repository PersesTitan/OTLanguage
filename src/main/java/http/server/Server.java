package http.server;

import http.items.Color;
import http.items.HttpRepository;

public class Server implements HttpRepository {
    public static HttpServerManager httpServerManager;

    public Server() {
        httpServerManager = new HttpServerManager();
        httpServerManager.start();
        if (pathMap.isEmpty()) {
            System.out.printf("%s경로가 존재하지 않습니다.%s\n", Color.RED, Color.RESET);
            httpServerManager.stop();
        }
    }

    public Server(int port) {
        httpServerManager = new HttpServerManager(port);
        httpServerManager.start();
        if (pathMap.isEmpty()) {
            System.out.printf("%s경로가 존재하지 않습니다.%s\n", Color.RED, Color.RESET);
            httpServerManager.stop();
        }
    }
}