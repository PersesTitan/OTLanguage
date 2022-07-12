package http.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UriParser {
    public void parsesQuery(String query, Map<String, Object> parameter) throws UnsupportedEncodingException {
        if (query != null) {
            for (String pair : query.split("&")) {
                var params = pair.split("=");
                String key = null;
                String value = null;
                if (params.length > 0) key = URLDecoder.decode(params[0], System.getProperty("file.encoding"));
                if (params.length > 1) value = URLDecoder.decode(params[1], System.getProperty("file.encoding"));

                if (parameter.containsKey(key)) {
                    Object obj = parameter.get(key);
                    if(obj instanceof List<?>) return;
                    else if (obj instanceof String) {
                        List<String> values = new ArrayList<>();
                        values.add((String) obj);
                        values.add(value);
                        parameter.put(key, values);
                    }
                } else parameter.put(key, value);
            }
        }
    }
}
