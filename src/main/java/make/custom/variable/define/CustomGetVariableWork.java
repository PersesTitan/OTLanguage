package make.custom.variable.define;

import java.util.Map;

public interface CustomGetVariableWork {
    boolean check(String line);
    String start(String line, Map<String, Map<String, Object>>... repository);
}
