package http.handler;

import http.define.HttpMethodType;

import java.util.Map;

public record HandlerDao(String value, String path, Map<String, Object> parameters) {
}
