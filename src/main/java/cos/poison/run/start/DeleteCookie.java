package cos.poison.run.start;

import bin.token.LoopToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.work.PoisonStartWork;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCookie implements RootWork, LoopToken, PoisonStartWork {
    private final int typeLen;
    private final Matcher matcher;
    public HttpExchange exchange;
    public Headers responseHeader;
    public AtomicInteger statCode;
    public AtomicReference<String> nowPath;

    public DeleteCookie(String clasName, String type1, String type2) {
        // ㅍㅇㅍ~ㄱㅋㄱ-쿠키이름
        // ㅍㅇㅍ~ㄱㅋㄱ-쿠키이름[]
        String pattern = clasName + ACCESS + type1 + type2;
        String patternText = startMerge(pattern);
        this.typeLen = pattern.replace("\\", "").length();
        this.matcher = Pattern.compile(patternText).matcher("");
    }

    @Override
    public void setExchange(HttpExchange exchange) {
        this.responseHeader = exchange.getResponseHeaders();
        this.exchange = exchange;
    }

    @Override
    public void setStatCode(AtomicInteger statCode) {
        this.statCode = statCode;
    }

    @Override
    public void setNowPath(AtomicReference<String> nowPath) {
        this.nowPath = nowPath;
    }

    @Override
    public boolean check(String line) {
        return matcher.reset(line).find();
    }

    @Override
    public void start(String line, String origen,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String cookieName = line.substring(this.typeLen).strip();
        String path = null;
        if (cookieName.contains("[") && cookieName.endsWith("]")) {
            String[] tokens = matchSplitError(cookieName, BL, 2);
            cookieName = tokens[0];
            path = bothEndCut(tokens[1], 0, 1);
        }
        deleteCookie(responseHeader, cookieName, path);
    }

    @Override
    public void first() {

    }
}
