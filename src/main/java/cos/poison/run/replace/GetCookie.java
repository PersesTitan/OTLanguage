package cos.poison.run.replace;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.work.PoisonStartWork;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GetCookie extends ReturnWorkV3 implements PoisonStartWork, RootWork {
    private Headers requestHeader;

    public GetCookie(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        return getCookie(requestHeader, params[0]);
    }

    @Override
    public void setData(HttpExchange exchange, AtomicInteger statCode,
                             AtomicReference<String> nowPath) {
        this.requestHeader = exchange.getRequestHeaders();
    }
}
