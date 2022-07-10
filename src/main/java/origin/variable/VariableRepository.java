package origin.variable;

import http.variable.HttpWork;

import java.util.*;

public interface VariableRepository {
    List<HttpWork> varList = new ArrayList<>();

    Map<String, Map<String, Object>> varRep = new HashMap<>();
    Set<String> set = new HashSet<>();

    default void setVariable(String line) throws Exception {
        for (HttpWork httpWork : varList) {
            if (httpWork.check(line)) {
                httpWork.start(line);
                break;
            }
        }
    }
}
