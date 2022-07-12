package http.server;

import com.sun.net.httpserver.HttpServer;
import http.handler.HttpGetHandler;
import http.handler.HttpPostHandler;
import http.items.Temporary;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerManager implements Temporary {
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
            Temporary.pathMap.forEach((k, v) -> server.createContext(k, new RootHandler()));
        } catch (IOException e) {
            System.out.println("서버 생성에 실패하였습니다.");
        }
    }
}
