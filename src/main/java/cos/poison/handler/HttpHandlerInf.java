package cos.poison.handler;

import com.sun.net.httpserver.HttpExchange;

public interface HttpHandlerInf {
    HandlerDao handle(HttpExchange exchange);
}
