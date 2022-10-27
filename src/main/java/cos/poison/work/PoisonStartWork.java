package cos.poison.work;

import com.sun.net.httpserver.HttpExchange;
import cos.poison.run.replace.GetCookie;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public interface PoisonStartWork {
    void setData(HttpExchange exchange, AtomicInteger statCode, AtomicReference<String> nowPath);
}
