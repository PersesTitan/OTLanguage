package cos.poison.run.start;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.run.replace.GetCookie;
import cos.poison.work.PoisonStartWork;
import work.v3.StartWorkV3;

import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Redirect extends StartWorkV3 implements RootWork, PoisonStartWork {
    private Headers responseHeader;
    private AtomicInteger statCode;
    // 1
    public Redirect(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        statCode.set(HttpURLConnection.HTTP_MOVED_TEMP);
        redirect(responseHeader, params[0]);
    }

    @Override
    public void setData(HttpExchange exchange,
                             AtomicInteger statCode, AtomicReference<String> nowPath) {
        this.statCode = statCode;
        this.responseHeader = exchange.getResponseHeaders();
    }
}
