package cos.poison.root;

import bin.apply.Repository;
import bin.apply.sys.make.StartLine;
import bin.exception.ServerException;
import bin.orign.variable.origin.put.SetVariableValue;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import cos.http.controller.HttpMethod;
import cos.http.controller.HttpRepository;
import cos.poison.handler.HttpHandlerInf;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static bin.apply.Controller.getSetVariable;
import static bin.apply.Setting.COPY_REPOSITORY;
import static cos.poison.Poison.variableHTML;
import static cos.poison.controller.HttpServerManager.getHttpHandlerInf;

public class HandlerRoot implements HttpHandler, HttpRepository, SetVariableValue {
    private final Map<String, Map<String, Object>> repository = (Map<String, Map<String, Object>>) COPY_REPOSITORY.clone();
    // 동작 파일
    private final String fileName;
    private final String startFinalTotal;
    // 비교 파일
    private final String path;
    private final HttpMethod method;
    private final HttpHandlerInf handlerInf;
    // 메개 변수
    private final String[][] params;
    // HTML
    private final String html;
    private final String contentType;

    // 생성자
    public HandlerRoot(String path, HttpMethod method,
                       String[] total, String[][] params,
                       String html, String contentType) {
        this.fileName = total[0];
        String startTotal = LOOP_TOKEN.get(this.fileName);
        int startLine = startTotal.indexOf("\n" + total[1] + " ");
        int endLine = startTotal.indexOf("\n" + total[2] + " ");
        this.startFinalTotal = startTotal.substring(startLine, endLine);

        this.path = path;
        this.method = method;
        this.handlerInf = getHttpHandlerInf(this.method);

        this.params = params;
        this.html = html;
        this.contentType = contentType;
    }

    // 동작
    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        if (this.path.equals(path) && method.equals(this.method.name())) {
            try (exchange; OutputStream responsive = exchange.getResponseBody()) {
                // 정보 받아오기
                var handlerDao = handlerInf.handle(exchange);
                // 저장공간 생성
                Map<String, Object> parameters = handlerDao.parameters();
                setParameters(parameters);

                printLog(this.method, path, handlerDao.value());
                serverStart(repository);

                String htmlTotal = Files.readString(Path.of(html));
                ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(variableHTML.replace(htmlTotal));

                int contentLen = byteBuffer.limit();
                byte[] content = new byte[contentLen];
                byteBuffer.get(content, 0, contentLen);

                Headers headers = exchange.getResponseHeaders();
                headers.add("Content-Type", contentType);
                headers.add("Content-Length", String.valueOf(contentLen));

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLen);
                responsive.write(content);
            } catch (IOException e) {throw ServerException.serverReadError();}
        } else {
            if (!path.equals("/favicon.ico")) {
                String message = "[" + method + "][" + path + "]가 정의되어 있지 않습니다.";
                System.out.println(message);
            }
        }
    }

    private void setParameters(Map<String, Object> parameters) {
        repository.values().forEach(Map::clear);
        for (var param : params) {
            String paramsName = param[2];
            set(param[0], param[1], parameters.getOrDefault(paramsName, DEFAULT.get(param[0])).toString(), repository);
//            if (parameters.containsKey(paramsName) && parameters.get(paramsName) != null
//                    && getSetVariable.getType(param[0], parameters.get(paramsName).toString())) {
//
//            }
        }
    }

    private void serverStart(Map<String, Map<String, Object>> repository) {
        variableHTML.reset();
        StartLine.startPoison(startFinalTotal, fileName, repository, Repository.repository);
    }
}
