package cos.poison.handler;

import java.util.Map;

public record HandlerDao(String value, String path, Map<String, Object> parameters) { }
