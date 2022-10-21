package cos.poison.work;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import work.StartWork;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public interface PoisonStartWork extends StartWork {
    void setExchange(HttpExchange exchange);
    void setResponseHeader(Headers responseHeader);
    void setStatCode(AtomicInteger statCode);
    void setNowPath(AtomicReference<String> nowPath);
}
