package cos.poison.controller;

import bin.apply.Setting;
import bin.apply.sys.make.ChangeHangle;
import bin.token.MergeToken;
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
import java.util.HashMap;
import java.util.Map;

import static bin.token.VariableToken.*;

public class HttpServerManager implements HttpRepository, MergeToken, ChangeHangle {
    public final static UriParser uriParser = new UriParser();
    public final static HttpHandlerInf httpGetHandler = new HttpGetHandler();
    public final static HttpHandlerInf httpPostHandler = new HttpPostHandler();
    public final static Map<HttpMethod, Map<String, HandlerItem>> httpMethod = new HashMap<>();
    static {for (HttpMethod method : HttpMethod.values()) httpMethod.put(method, new HashMap<>());}

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
                httpServer.createContext("/", new HandlerRoot())
                        .getServer().start();
                System.out.printf("URL http://%s:%d/\n", host, port);
                startServerPrint();
            } catch (Exception e) {Setting.errorMessage("서버 실행에 실패하였습니다.");}
        } else Setting.errorMessage("서버가 존재하지 않습니다.");
    }

    // "text/html;charset=UTF-8"
    // POST 추가
    public void addPost(String path, String[] total, String[][] params, String html) {
        if (httpServer != null)
            putHttpMethod(HttpMethod.POST, path,
                    new HandlerItem(total[0], getLoopTotal(total), params, html, null));
        else Setting.errorMessage("서버가 존재하지 않습니다.");
    }

    // GET 추가
    public void addGet(String path, String[] total, String[][] params, String html) {
        if (httpServer != null)
            putHttpMethod(HttpMethod.GET, path,
                    new HandlerItem(total[0], getLoopTotal(total), params, html, null));
        else Setting.errorMessage("서버가 존재하지 않습니다.");
    }

    private void putHttpMethod(HttpMethod method, String path, HandlerItem value) {
        String numberType = String.format("(?<$0>%s)", "[0-9]+");
        String otherType = String.format("(?<$0>%s)", "[^/]+");
        path = (path.endsWith("/") ? path : path + "/")
                .replaceAll(INT_VARIABLE + VARIABLE_PUT + VARIABLE_HTML, numberType)
                .replace("<" + INT_VARIABLE + VARIABLE_PUT, "<")
                .replaceAll(VARIABLE_PUT + VARIABLE_HTML, otherType)
                .replace("<" + VARIABLE_PUT, "<");
        HttpServerManager.httpMethod.get(method).put(checkPattern(change(path)), value);
    }

    // ============================================= //
    public static HttpHandlerInf getHttpHandlerInf(HttpMethod httpMethod) {
        return switch (httpMethod) {
            case POST -> httpPostHandler;
            case GET -> httpGetHandler;
        };
    }
}
