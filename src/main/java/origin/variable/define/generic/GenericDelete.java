package origin.variable.define.generic;

import java.util.Map;

public interface GenericDelete {
    boolean check(String line);
    void start(String line, Map<String, Map<String, Object>> repository);
}
