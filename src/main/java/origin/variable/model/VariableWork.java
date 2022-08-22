package origin.variable.model;

import java.util.Map;
import java.util.Set;

public interface VariableWork {
    boolean check(String line);
    void start(String line,
               Map<String, Map<String, Object>> repository,
               Set<String> set);
}
