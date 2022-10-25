package cos.poison.handler;

import bin.token.MergeToken;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class UriParser implements MergeToken {
    public void parsesQuery(String query, Map<String, Object> parameter) throws UnsupportedEncodingException {
        if (query != null) {
            if (query.startsWith("{") && query.endsWith("}")) setJSON(query, parameter);
            else setUrl(query, parameter);
        }
    }

    private void setUrl(String query, Map<String, Object> parameter) throws UnsupportedEncodingException {
        for (String pair : query.split("&")) {
            var params = pair.split("=");
            String key = null;
            String value = "";
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

    private void setJSON(String query, Map<String, Object> parameter) {
        // { key: value } => key: value
        StringTokenizer values = new StringTokenizer(bothEndCut(query), ",");
        while (values.hasMoreTokens()) {
            // key, value
            String[] token = matchSplitError(values.nextToken(), ":", 2);
            String key = token[0].strip();
            String value = token[1].strip();
            parameter.put(
                    key.startsWith("\"") && key.endsWith("\"") ? bothEndCut(key) : key,
                    value.startsWith("\"") && value.endsWith("\"") ? bothEndCut(value) : value);
        }
    }
}
