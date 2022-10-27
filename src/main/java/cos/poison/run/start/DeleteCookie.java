package cos.poison.run.start;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.run.replace.GetCookie;
import cos.poison.work.PoisonStartWork;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class DeleteCookie extends StartWorkV3
        implements PoisonStartWork, RootWork {
    private Headers responseHeader;
    // 1, 2
    public DeleteCookie(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        deleteCookie(responseHeader, params[0], params.length == 2 ? params[1] : null);
    }

    @Override
    public void setData(HttpExchange exchange, AtomicInteger statCode,
                             AtomicReference<String> nowPath) {
        this.responseHeader = exchange.getResponseHeaders();
    }
}
