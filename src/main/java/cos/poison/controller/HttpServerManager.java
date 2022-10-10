package cos.poison.controller;

import bin.apply.Setting;
import com.sun.net.httpserver.HttpServer;
import cos.http.controller.HttpMethod;
import cos.http.controller.HttpRepository;
import cos.poison.handler.HttpGetHandler;
import cos.poison.handler.HttpHandlerInf;
import cos.poison.handler.HttpPostHandler;
import cos.poison.handler.UriParser;
import cos.poison.root.HandlerRoot;


import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerManager implements HttpRepository {
    public final static UriParser uriParser = new UriParser();
    public final static HttpHandlerInf httpGetHandler = new HttpGetHandler();
    public final static HttpHandlerInf httpPostHandler = new HttpPostHandler();

    public static HttpServer httpServer;
    public String host = "localhost";
    public int port = 9090;

    // 생성 로직
    public void createServer(String host, int port) {this.host = host; this.port = port; create();}
    public void createServer(String host) {this.host = host; create();}
    public void createServer(int port) {this.port = port; create();}
    public void createServer() {create();}
    private void create() {
        try {httpServer = HttpServer.create(new InetSocketAddress(host, port), 0);}
        catch (IOException e) {Setting.errorMessage("서버 생성에 실패하였습니다.");}
    }

    // 시작 로직
    public void start() {
        if (httpServer != null) {
            try {
                httpServer.start();
                System.out.printf("URL http://%s:%d/\n", host, port);
                startServerPrint();
            } catch (Exception e) {Setting.errorMessage("서버 실행에 실패하였습니다.");}
        } else Setting.errorMessage("서버가 존재하지 않습니다.");
    }

    // POST 추가
    public void addPost(String path, String[] total, String[][] params, String html) {
        if (httpServer != null) httpServer.createContext(path,
                new HandlerRoot(path, HttpMethod.POST, total, params, html, "text/html;charset=UTF-8"));
        else Setting.errorMessage("서버가 존재하지 않습니다.");
    }

    // GET 추가
    public void addGet(String path, String[] total, String[][] params, String html) {
        if (httpServer != null) httpServer.createContext(path,
                new HandlerRoot(path, HttpMethod.GET, total, params, html, "text/html;charset=UTF-8"));
        else Setting.errorMessage("서버가 존재하지 않습니다.");
    }

    public static HttpHandlerInf getHttpHandlerInf(HttpMethod httpMethod) {
        return switch (httpMethod) {
            case POST -> httpPostHandler;
            case GET -> httpGetHandler;
        };
    }
}
