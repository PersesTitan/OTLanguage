package cos.poison.controller;

import bin.exception.MatchException;
import bin.exception.ServerException;
import bin.exception.VariableException;
import bin.token.LoopToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import cos.http.controller.HttpMethod;
import cos.http.controller.HttpRepository;
import cos.poison.handler.HandlerDao;
import cos.poison.handler.HttpHandlerInf;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static bin.apply.Repository.getSet;
import static cos.poison.controller.HttpServerManager.createHTML;

public class RootHandler implements HttpHandler, HttpRepository, LoopToken {
    private final String contentType;
    private final String[] variables;
    private final HttpMethod httpMethod;
    private final HttpHandlerInf handlerInf;
    private final Map<String, Map<String, Object>>[] repositoryArray;

    public RootHandler(String contentType, String makeVariable,
                       HttpMethod httpMethod, HttpHandlerInf handlerInf,
                       Map<String, Map<String, Object>>[] repositoryArray) {
        this.httpMethod = httpMethod;
        this.handlerInf = handlerInf;
        this.contentType = contentType;
        this.repositoryArray = repositoryArray;
        this.variables = bothEndCut(makeVariable)
                .strip()
                .split(BLANK + BR + BL + BLANK);
    }

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        var repository = httpRepository.get(httpMethod);
        if (repository.containsKey(path)) {
            try (exchange; OutputStream responsive = exchange.getResponseBody()) {
                String filePath = createHTML.changeVariable(repository.get(path), repositoryArray);
                ByteBuffer byteBuffer = StandardCharsets.UTF_16.encode(filePath);

                int contentLen = byteBuffer.limit();
                byte[] content = new byte[contentLen];
                byteBuffer.get(content, 0, contentLen);

                Headers headers = exchange.getResponseHeaders();
                headers.add("Content-Type", contentType);
                headers.add("Content-Length", String.valueOf(contentLen));

                HandlerDao handle = handlerInf.handle(exchange);

                printLog(httpMethod, path, handle.value()); //get, /, name=get

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLen);
                responsive.write(content);

                startPath(handle);
            } catch (IOException ignored) {
                throw ServerException.serverReadError();
            }
        } else {
            if (!path.equals("/favicon.ico")) System.out.println(path + "가 정의되어 있지 않습니다.");
        }
    }

    private void startPath(HandlerDao handlerDao) {
        Map<String, Object> parameters = handlerDao.parameters();
        // [ㅇㅅㅇ 변수1:p1][ㅇㅈㅇ 변수2:p2] => ㅇㅅㅇ 변수1:p1][ㅇㅈㅇ 변수2:p2
        // ㅇㅅㅇ 변수1:p1, ㅇㅈㅇ 변수2:p2
        Set<String> set = new HashSet<>(); // 임시 변수
        var repository = repositoryArray[0];
        for (String variable : variables) {
            String[] vars = variable       // ㅇㅅㅇ, 변수1:p1
                    .strip()
                    .split(BLANKS, 2);
            if (vars.length != 2) throw MatchException.grammarError();

            String[] variableValue = vars[1].split(VARIABLE_GET_S, 2); // 변수1, p1
            if (variableValue.length != 2) throw MatchException.grammarError();
            String variableType = vars[0];                                  // ㅇㅅㅇ
            String variableName = variableValue[0];                         // 변수1
            String paramName = variableValue[1];                            // p1
            set.add(variableName);

            if (!repository.containsKey(variableType)) throw VariableException.noDefine();
            else if (getSet(repository).contains(variableName)) throw VariableException.sameVariable();
            var paramValue = parameters.getOrDefault(paramName, DEFAULT.get(variableType));
            repository.get(variable).put(variableName, paramValue);
        }
        String total = httpRepository.get(httpMethod).get(handlerDao.path());

        set.forEach(variableNames -> repository.values().forEach(v -> v.remove(variableNames)));
    }
}
