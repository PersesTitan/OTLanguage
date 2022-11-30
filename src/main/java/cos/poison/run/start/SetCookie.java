package cos.poison.run.start;

import bin.calculator.tool.Calculator;
import bin.exception.VariableException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import cos.poison.root.RootWork;
import cos.poison.work.PoisonStartWork;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SetCookie extends StartWorkV3 implements PoisonStartWork, RootWork, Calculator {
    private Headers responseHeader;
    // cookieKey, cookieValue, path, maxAge
    public SetCookie(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        switch (params.length) {
            case 2 -> setCookie(responseHeader, params[0], params[1], null, -1);
            case 3 -> {
                if (params[2].matches("[0-9]+")) setCookie(responseHeader, params[0], params[1], null, Integer.parseInt(params[2]));
                else setCookie(responseHeader, params[0], params[1], params[2], -1);
            }
            case 4 -> {
                double age = getNumber(params[3], repositoryArray);
                if (age >= 0)
                    setCookie(responseHeader, params[0], params[1], params[2], (int) age);
                else throw new VariableException().typeMatch();
            }
        }
    }

    @Override
    public void setData(HttpExchange exchange, AtomicInteger statCode, AtomicReference<String> nowPath) {
        this.responseHeader = exchange.getResponseHeaders();
    }
}
