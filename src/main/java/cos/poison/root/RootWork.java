package cos.poison.root;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;

public class RootWork {
    public void redirect(HttpExchange exchange, Headers headers, String newUrl) throws IOException {
        headers.set("Location", newUrl);
        exchange.sendResponseHeaders(302, 0);
    }

    public void setCookie(Headers headers, String key, String value, String path, int maxAge) {
        StringBuilder cookie = new StringBuilder();
        cookie.append(key).append("=").append(value);
        if (maxAge != -1) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.SECOND, maxAge);
            cookie.append("; expires=").append(calendar.getTime()).append("; Max-Age=").append(maxAge);
        }
        cookie.append("; path=").append(Objects.requireNonNullElse(path, "/"));
        headers.add("Set-Cookie", cookie.toString());
    }


    public void getCookie(Headers headers, String key) {
        StringTokenizer cookies = new StringTokenizer(headers.get("Cookie").get(0), ";");
        while (cookies.hasMoreTokens()) {
            String[] tokens = cookies.nextToken().split("=", 2)[0]
        }

    }

}
