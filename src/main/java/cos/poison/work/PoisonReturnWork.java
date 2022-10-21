package cos.poison.work;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import work.ReturnWork;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public interface PoisonReturnWork extends ReturnWork {
    void setExchange(HttpExchange exchange);
    void setRequestHeader(Headers requestHeader);
    void setStatCode(AtomicInteger statCode);
    void setNowPath(AtomicReference<String> nowPath);
}
