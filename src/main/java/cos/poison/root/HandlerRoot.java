package cos.poison.root;

import bin.apply.Repository;
import bin.apply.sys.make.StartLine;
import bin.orign.variable.origin.put.SetVariableValue;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import cos.http.controller.HttpMethod;
import cos.http.controller.HttpRepository;
import cos.poison.controller.HandlerItem;
import cos.poison.method.PoisonTools;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static bin.apply.Setting.*;
import static bin.apply.sys.make.StartLine.errorCount;
import static bin.apply.sys.make.StartLine.errorLine;
import static cos.poison.Poison.variableHTML;
import static cos.poison.PoisonRepository.poisonReturnWorks;
import static cos.poison.PoisonRepository.poisonStartWorks;
import static cos.poison.controller.HttpServerManager.*;

public class HandlerRoot implements HttpHandler, HttpRepository, SetVariableValue, PoisonTools {
    private final Map<String, Map<String, Object>> repository = (Map<String, Map<String, Object>>) COPY_REPOSITORY.clone();
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
                serverStart(repository, statusCode, nowPath,
                        exchange, requestHeader, responseHeader,
                        handlerItem.startFinalTotal(),
                        handlerItem.fileName());
                exchange.sendResponseHeaders(statusCode.get(),0);
                responseBody.write(getBody(handlerItem.responseValue()));
            } else {
                // ==== 에러 동작 ====
                if (!path.equals("/favicon.ico"))
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
            return variableHTML.replace(Files.readString(Path.of(getHtml(responseValue)))).getBytes();
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

    private void serverStart(Map<String, Map<String, Object>> repository,
                             AtomicInteger statCode, AtomicReference<String> nowPath,
                             HttpExchange exchange,
                             Headers requestHeader, Headers responseHeader,
                             String startFinalTotal, String fileName) {
        poisonReturnWorks.forEach(v -> {
            v.setExchange(exchange);
            v.setNowPath(nowPath);
            v.setRequestHeader(requestHeader);
            v.setStatCode(statCode);
            returnWorks.add(v);
        });

        poisonStartWorks.forEach(v -> {
            v.setExchange(exchange);
            v.setNowPath(nowPath);
            v.setResponseHeader(responseHeader);
            v.setStatCode(statCode);
            startWorks.add(v);
        });

        startWorks.add(variableHTML.reset());
        try {
            StartLine.startPoison(startFinalTotal, fileName, repository, Repository.repository);
        } catch (Exception e) {
            String error = String.format("Error Line %d (%s)", errorCount.get(), errorLine.get());
            errorMessage(error);
        } finally {
            poisonReturnWorks.forEach(returnWorks::remove);
            poisonStartWorks.forEach(startWorks::remove);
            startWorks.remove(variableHTML);
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
            }
            return null;
        }
    }
}
