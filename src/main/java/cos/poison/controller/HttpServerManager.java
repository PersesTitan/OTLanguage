package cos.poison.controller;

import bin.apply.sys.item.Color;
import com.sun.net.httpserver.HttpServer;
import cos.http.controller.HttpMethod;
import cos.http.controller.HttpRepository;
import cos.poison.handler.HttpGetHandler;
import cos.poison.handler.HttpHandlerInf;
import cos.poison.handler.HttpPostHandler;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

public record HttpServerManager(String host, int port) implements HttpRepository {
    public final static CreateHTML createHTML = new CreateHTML();
    public final static UriParser uriParser = new UriParser();
    public final static HttpHandlerInf httpGetHandler = new HttpGetHandler();
    public final static HttpHandlerInf httpPostHandler = new HttpPostHandler();
    private static HttpServer httpServer;

    public void start() {
        if (httpServer != null) {
            httpServer.start();
            startServerPrint();
        } else System.out.printf("%s서버가 존재하지 않습니다.%s\n", Color.RED, Color.RESET);
    }

    // path : /sub

    public void addHttp(HttpMethod method, String path, String total,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        if (httpServer != null) {
            httpServer.createContext(path,
                    new RootHandler("text/html;charset=UTF-8",
                            total, method,
                            getHttpHandlerInf(method),
                            repositoryArray));
        }
    }

    private HttpHandlerInf getHttpHandlerInf(HttpMethod httpMethod) {
        return switch (httpMethod) {
            case POST -> httpPostHandler;
            case GET -> httpGetHandler;
        };
    }

    private void createServer() {
        System.out.printf("URL http://%s:%d/%n", host, port);
        try {
            httpServer = HttpServer.create(new InetSocketAddress(host, port), 0);
            httpRepository.put(HttpMethod.GET, new HashMap<>());
            httpRepository.put(HttpMethod.POST, new HashMap<>());
            Runtime.getRuntime().addShutdownHook(new Thread(() -> httpServer.stop(0)));
        } catch (IOException e) {
            System.out.printf("%s서버 생성에 실패하였습니다.%s\n", Color.RED, Color.RESET);
        }
    }
}
