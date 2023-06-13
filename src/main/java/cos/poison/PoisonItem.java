package cos.poison;

import bin.apply.item.Item;
import bin.token.ColorToken;
import bin.token.EditToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import cos.poison.tool.HandlerRoot;
import cos.poison.tool.RexMap;
import cos.poison.type.MethodType;
import lombok.AccessLevel;
import lombok.Setter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.regex.Matcher;

@Setter
public class PoisonItem implements Item {
    // save url
    private final Map<MethodType, Map<String, BiFunction<HttpExchange, AtomicInteger, Object>>> method = new HashMap<>();
    private final Map<MethodType, RexMap> rex = new HashMap<>();
    // load page
    private final AtomicReference<HttpExchange> exchange = new AtomicReference<>(null);

    // put method path
    public void put(MethodType method, String path, BiFunction<HttpExchange, AtomicInteger, Object> function) {
        if (this.method.containsKey(method)) {
            Map<String, BiFunction<HttpExchange, AtomicInteger, Object>> map = this.method.get(method);
            if (map.containsKey(path)) throw PoisonException.HAVE_PATH_ERROR.getThrow(path);
            else map.put(path, function);
        } else this.method.put(method, new HashMap<>(Map.of(path, function)));
    }

    // put rex path
    public void put(MethodType method, Matcher matcher, BiFunction<HttpExchange, AtomicInteger, Object> function) {
        if (rex.containsKey(method)) rex.get(method).put(matcher, function);
        else rex.put(method, new RexMap(matcher, function));
    }

    // get read data
    public byte[] get(MethodType method, String path, HttpExchange exchange, AtomicInteger status) {
        if (this.method.containsKey(method)) {
            Map<String, BiFunction<HttpExchange, AtomicInteger, Object>> map = this.method.get(method);
            if (map.containsKey(path)) return (byte[]) map.get(path).apply(exchange, status);
        }
        if (rex.containsKey(method)) return rex.get(method).start(path, exchange, status);
        status.set(HttpURLConnection.HTTP_NOT_FOUND);
        return null;
    }

    public void setExchange(HttpExchange exchange) {
        this.exchange.set(exchange);
    }

    private HttpExchange getExChange() {
        HttpExchange exchange = this.exchange.get();
        if (exchange == null) throw PoisonException.DO_NOT_RUN.getThrow(null);
        return exchange;
    }

    // poison methods
    public void setCookie(String key, String value, String path, int age) {
        StringBuilder cookie = new StringBuilder();
        cookie.append(key).append('=').append(value);
        // set time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, age);
        cookie.append("; expires=").append(calendar.getTime());
        cookie.append("; Max-Age=").append(age);
        // set path
        cookie.append("; path=").append(path);
        getExChange().getResponseHeaders().add("Set-Cookie", cookie.toString());
    }

    public void setCookie(String key, String value, String path) {
        getExChange().getResponseHeaders().add("Set-Cookie", String.format("%s=%s; path=%s", key, value, path));
    }

    public void setCookie(String key, String value) {
        setCookie(key, value, "/");
    }

    public String getCookie(String key) {
        Headers headers = getExChange().getRequestHeaders();
        List<String> cookie = headers.getOrDefault("Cookie", null);
        if (cookie == null || cookie.isEmpty()) return "";
        for (String cookieItem : cookie) {
            StringTokenizer cookies = new StringTokenizer(cookieItem, ";");
            while (cookies.hasMoreTokens()) {
                String[] tokens = EditToken.split(cookies.nextToken(), '=');
                if (tokens[0].strip().equals(key)) return tokens[1];
            }
        }
        return "";
    }

    public boolean isCookie(String key) {
        Headers headers = getExChange().getRequestHeaders();
        if (headers.containsKey("Cookie")) {
            List<String> cookie = headers.get("Cookie");
            if (cookie.isEmpty()) return false;
            for (String cookieItem : cookie) {
                StringTokenizer cookies = new StringTokenizer(cookieItem, ";");
                while (cookies.hasMoreTokens()) {
                    String[] tokens = EditToken.split(cookies.nextToken(), '=');
                    if (tokens[0].strip().equals(key)) return true;
                }
            }
        }
        return false;
    }

    public void deleteCookie(String key, String path) {
        getExChange()
                .getResponseHeaders()
                .add("Set-Cookie", key + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=" + path);
    }

    public void redirect(String path) {
        getExChange().getResponseHeaders().add("Location", path);
    }

    @Setter(AccessLevel.NONE)
    private HttpServer server = null;
    private String host = "localhost";
    private int port = 9090;

    // server
    public void create() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        } catch (IOException e) {
            throw PoisonException.CREATE_SERVER_ERROR.getThrow(null);
        }
    }

    public void start() {
        if (this.server != null) {
            this.server.createContext("/", new HandlerRoot(this));
            this.server.start();
            System.out.printf("URL http://%s:%d/\n", this.host, this.port);
            System.out.printf(
                    """
                    %s╭───────────╮╭──╮╭────────╮╭──────────╮╭─────────╮%s
                    %s│  ╭─────╮  │╰──╯│  ╭─────╯│  ╭────╮  ││  ╭───╮  │%s
                    %s│  ╰─────╯  │╭──╮│  ╰─────╮│  │    │  ││  │   │  │%s
                    %s│  ┌────────╯│  │╰─────╮  ││  │    │  ││  │   │  │%s
                    %s│  │ ╭──────╮│  │╭─────╯  ││  ╰────╯  ││  │   │  │%s
                    %s│  │ │ ╭──╮ ││  │╰────────╯╰──────────╯╰──╯   ╰──╯%s
                    %s│  │ │ ╰──╯ ││  │%s        %s== OTLanguage ==%s
                    %s╰──╯ ╰──────╯╰──╯%s        %s==   Poison   ==%s
    
                    """,
                    ColorToken.PURPLE_BRIGHT, ColorToken.RESET, ColorToken.PURPLE_BRIGHT, ColorToken.RESET,
                    ColorToken.PURPLE_BRIGHT, ColorToken.RESET, ColorToken.PURPLE_BRIGHT, ColorToken.RESET,
                    ColorToken.PURPLE_BRIGHT, ColorToken.RESET, ColorToken.PURPLE_BRIGHT, ColorToken.RESET,
                    ColorToken.PURPLE_BRIGHT, ColorToken.RESET, ColorToken.PURPLE, ColorToken.RESET,
                    ColorToken.PURPLE_BRIGHT, ColorToken.RESET, ColorToken.PURPLE, ColorToken.RESET);
            printMessage("[Poison Server Start]");
        } else throw PoisonException.NO_CREATE_SERVER.getThrow(null);
    }

    public void stop() {
        if (this.server != null) {
            this.server.stop(0);
            printMessage("[Poison Server Stop]");
        } else throw PoisonException.NO_CREATE_SERVER.getThrow(null);
    }

    private void printMessage(String message) {
        PoisonToken.printTime();
        System.out.println(ColorToken.GREEN + message + ColorToken.RESET);
    }

    @Override
    public String toString() {
        return toString(PoisonToken.POISON);
    }
}
