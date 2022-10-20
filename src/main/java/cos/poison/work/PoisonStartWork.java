package cos.poison.work;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

public interface PoisonStartWork {
    boolean check(String line);
    void start(String line, String origen,
               HttpExchange exchange, Headers responseHeader,
               Map<String, Map<String, Object>>[] repositoryArray);
}
