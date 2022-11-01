package cos.poison.root;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.*;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public interface RootWork {
    default void redirect(Headers headers, String newUrl){
        headers.add("Location", newUrl);
    }

    default void setCookie(Headers headers, String key, String value, String path, int maxAge) {
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

    default void deleteCookie(Headers headers, String key, String path) {
        String cookie = key + "=" +
                "; expires=" + "Thu, 01 Jan 1970 00:00:00 GMT" +
                "; path=" + Objects.requireNonNullElse(path, "/");
        headers.add("Set-Cookie", cookie);
    }

    default String getCookie(Headers headers, String key) {
        List<String> cookie = headers.getOrDefault("Cookie", null);
        if (cookie == null || cookie.isEmpty()) return null;
        StringTokenizer cookies = new StringTokenizer(cookie.get(0), ";");
        while (cookies.hasMoreTokens()) {
            String[] tokens = cookies.nextToken().split("=", 2);
            if (tokens[0].strip().equals(key)) return tokens[1];
        }
        return null;
    }

    default String isEmptyCookie(Headers headers, String key) {
        return getCookie(headers, key) != null ? TRUE : FALSE;
    }
}
