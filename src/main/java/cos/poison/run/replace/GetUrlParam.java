package cos.poison.run.replace;

import bin.apply.sys.make.ChangeHangle;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.work.PoisonStartWork;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetUrlParam extends ReturnWorkV3
        implements PoisonStartWork, ChangeHangle {
    private HttpExchange exchange;
    private AtomicReference<String> nowPath;

    public GetUrlParam(int... counts) {
        super(counts);
    }

    @Override
    public void setData(HttpExchange exchange, AtomicInteger statCode,
                             AtomicReference<String> nowPath) {
        this.exchange = exchange;
        this.nowPath = nowPath;
    }

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String url = exchange.getRequestURI().getPath();
        Matcher urlMatcher = Pattern.compile(nowPath.get()).matcher(url.endsWith("/") ? url : url + "/");
        if (urlMatcher.find()) return urlMatcher.group(change(params[0]));
        else return null;
    }
}
