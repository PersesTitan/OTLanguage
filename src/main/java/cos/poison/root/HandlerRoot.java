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
import cos.poison.handler.HandlerDao;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static bin.apply.Setting.COPY_REPOSITORY;
import static bin.apply.Setting.warringMessage;
import static cos.poison.Poison.variableHTML;
import static cos.poison.controller.HttpServerManager.*;

public class HandlerRoot implements HttpHandler, HttpRepository, SetVariableValue {
    private final Map<String, Map<String, Object>> repository = (Map<String, Map<String, Object>>) COPY_REPOSITORY.clone();

    // 동작
    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        path = path.endsWith("/") ? path : path + "/";
        try (exchange; OutputStream responseBody = exchange.getResponseBody()) {
            HttpMethod method = HttpMethod.valueOf(exchange.getRequestMethod());

            Headers responseHeader = exchange.getResponseHeaders(); // 응답(send)
            Headers requestHeader = exchange.getRequestHeaders();   // 요청(get)
            HandlerItem handlerItem = checkHandlerItem(method, path);
            if (handlerItem != null) {
                // 정보 가져오기
                var handlerDao = getHttpHandlerInf(method).handle(exchange);
                Map<String, Object> parameters = handlerDao.parameters();
                setParameters(parameters, handlerItem.params());
                // 로그 출력
                printLog(method, path, handlerDao.value());
                // 동작
                serverStart(repository, handlerItem.startFinalTotal(), handlerItem.fileName());

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                String responseValue = handlerItem.responseValue();
                if (responseValue.endsWith(".html")) responseBody.write(html(responseValue));
                else responseBody.write(responseValue.getBytes());
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

//        String path = exchange.getRequestURI().getPath();
//        String methodType = exchange.getRequestMethod();

//        var map = methodType.equals(POST.name()) ? postMap : getMap;
//        var method = methodType.equals(POST.name()) ? POST : GET;
//        if (map.containsKey(path)) {
//            var handlerItem = map.get(path);
//            try (exchange; OutputStream responsive = exchange.getResponseBody()) {
//                // 정보 받아오기
//                var handlerDao = getHttpHandlerInf(method).handle(exchange);
//                // 저장공간 생성
//                Map<String, Object> parameters = handlerDao.parameters();
//                setParameters(parameters, handlerItem.params());
//
//                printLog(method, path, handlerDao.value());
//                serverStart(repository, handlerItem.startFinalTotal(), handlerItem.fileName());
//
//                String htmlTotal = Files.readString(Path.of(handlerItem.html()));
//                ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(variableHTML.replace(htmlTotal));
//
//                int contentLen = byteBuffer.limit();
//                byte[] content = new byte[contentLen];
//                byteBuffer.get(content, 0, contentLen);
//
//                Headers headers = exchange.getResponseHeaders();
//                headers.add("Content-Type", handlerItem.contentType());
//                headers.add("Content-Length", String.valueOf(contentLen));
//
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLen);
//                responsive.write(content);
//            } catch (IOException e) {throw ServerException.serverReadError();}
//        } else {
//            if (!path.equals("/favicon.ico")) warringMessage("[" + method + "][" + path + "]가 정의되어 있지 않습니다.");
//        }
    }

    // Body Value
    private byte[] html(String responseValue) throws IOException {
        String htmlTotal = Files.readString(Path.of(responseValue));
        return variableHTML.replace(htmlTotal).getBytes();
    }

    private void setParameters(Map<String, Object> parameters, String[][] params) {
        repository.values().forEach(Map::clear);
        for (var param : params)
            set(param[0], param[1], parameters.getOrDefault(param[2], DEFAULT.get(param[0])).toString(), repository);
    }

    private void serverStart(Map<String, Map<String, Object>> repository,
                             String startFinalTotal, String fileName) {
        variableHTML.reset();
        StartLine.startPoison(startFinalTotal, fileName, repository, Repository.repository);
    }

    private HandlerItem checkHandlerItem(HttpMethod method, String path) {
        var repository = httpMethod.get(method);
        if (repository.containsKey(path)) return repository.get(path);
        else {
            for (Map.Entry<String, HandlerItem> entry : repository.entrySet()) {
                if (path.matches(entry.getKey())) return entry.getValue();
            }
            return null;
        }
    }
}
