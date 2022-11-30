package cos.poison.root;

import bin.apply.Repository;
import bin.apply.Setting;
import bin.apply.sys.item.HpMap;
import bin.apply.sys.item.RunType;
import bin.apply.sys.make.StartLine;
import bin.exception.*;
import bin.orign.variable.SetVariableValue;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import cos.poison.controller.HttpMethod;
import cos.poison.controller.HttpRepository;
import cos.poison.controller.HandlerItem;
import cos.poison.method.PoisonTools;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static bin.apply.Setting.*;
import static bin.apply.sys.item.SystemSetting.createStartWorks;
import static bin.apply.sys.make.StartLine.*;
import static cos.poison.PoisonRepository.*;
import static cos.poison.controller.HttpServerManager.*;

public class HandlerRoot implements HttpHandler, HttpRepository, SetVariableValue, PoisonTools {
    private final Map<String, Map<String, Object>> repository = new HashMap<>() {{
        repositoryItems.forEach(v -> put(v, new HpMap(v)));
    }};
    private final String defaultHtml;
    public HandlerRoot(String defaultHtml) {
        this.defaultHtml = defaultHtml;
    }

    // 동작
    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        path = path.endsWith("/") ? path : path + "/";
        try (exchange; OutputStream responseBody = exchange.getResponseBody()) {
            // 기본값 : StatusCode = 200, Path = /
            final AtomicInteger statusCode = new AtomicInteger(HttpURLConnection.HTTP_OK);
            final AtomicReference<String> nowPath = new AtomicReference<>(path);
            // method 종류 구하기
            final HttpMethod method = HttpMethod.valueOf(exchange.getRequestMethod());

            final Headers responseHeader = exchange.getResponseHeaders(); // 응답(send)
            final Headers requestHeader = exchange.getRequestHeaders();   // 요청(get)

            final HandlerItem handlerItem = checkHandlerItem(method, path, nowPath);
            if (defaultHtml != null) {
                exchange.sendResponseHeaders(statusCode.get(),0);
                responseBody.write(defaultHtml.getBytes());
            }

            if (defaultHtml == null && handlerItem != null) {
                if (handlerItem.contentType() != null)
                    responseHeader.add("Content-Type", handlerItem.contentType());
                // 정보 가져오기
                var handlerDao = getHttpHandlerInf(method).handle(exchange);
                Map<String, Object> parameters = handlerDao.parameters();
                setParameters(parameters, handlerItem.params());
                // 로그 출력
                printLog(method, path, handlerDao.value());
                // 동작
                try {
                    Repository.repository.addFirst(this.repository);
                    serverStart(statusCode, nowPath, exchange,
                            handlerItem.startFinalTotal(), handlerItem.fileName());
                } finally {
                    Repository.repository.remove(this.repository);
                }
                exchange.sendResponseHeaders(statusCode.get(),0);
                responseBody.write(getBody(handlerItem.responseValue()));
            } else {
                // ==== 에러 동작 ====
                for (String url : passUrl) {
                    if (path.equals(url)) return;
                }

                warringMessage("[" + exchange.getRequestMethod() + "][" + path + "]가 정의되어 있지 않습니다.");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            }
        } catch (IllegalArgumentException e) {
            try {exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);}
            catch (IOException ignored) {}
        } catch (IOException ignored) {}
    }

    // Body Value
    private byte[] getBody(String responseValue) throws IOException {
        if (responseValue.endsWith(".html"))
            return VariableHTML
                    .getInstance()
                    .replace(Files.readString(Path.of(getHtml(responseValue))))
                    .getBytes();
        else return responseValue.getBytes(StandardCharsets.UTF_16);
    }

    private void setParameters(Map<String, Object> parameters, String[][] params) {
        repository.values().forEach(Map::clear);
        for (var param : params) {
            try {
                if (parameters.containsKey(param[2])) {
                    Object paramValue = parameters.getOrDefault(param[2], "");
                    set(param[0], param[1], paramValue.toString(), repository);
                }
            } catch (Exception ignored) {}
        }
    }

    private void serverStart(AtomicInteger statCode, AtomicReference<String> nowPath,
                             HttpExchange exchange, String startFinalTotal, String fileName) {
        poisonStartList.forEach(v -> v.setData(exchange, statCode, nowPath));
        Repository.startWorksV3.putAll(poisonStartWorks);
        Repository.returnWorksV3.putAll(poisonReturnWorks);
        createStartWorks(MODEL, "", VariableHTML.getInstance().reset());

        try {
            StartLine.startPoison(startFinalTotal, fileName, Repository.repository);
        } catch (VariableException | MatchException | ServerException | ConsoleException | CosException e) {
            RunType cpRunType = runType;
            runType = RunType.Shell;
            errorMessage(e, e);
            runType = cpRunType;
        } finally {
            Repository.startWorksV3.values().forEach(v -> poisonStartWorks.keySet().forEach(v::remove));
            Repository.returnWorksV3.values().forEach(v -> poisonReturnWorks.keySet().forEach(v::remove));
            startWorksV3.get(MODEL).remove("");
        }
    }

    private HandlerItem checkHandlerItem(HttpMethod method, String path, AtomicReference<String> nowPath) {
        var repository = httpMethod.get(method);
        if (repository.containsKey(path)) return repository.get(path);
        else {
            for (Map.Entry<String, HandlerItem> entry : repository.entrySet()) {
                if (path.matches(entry.getKey())) {
                    nowPath.set(entry.getKey());
                    return entry.getValue();
                }
            } return null;
        }
    }
}
