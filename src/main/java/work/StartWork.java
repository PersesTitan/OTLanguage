package work;

import java.io.Serializable;
import java.util.Map;

public interface StartWork extends Serializable {
    boolean check(String line);
    void start(String line, String origen,
               Map<String, Map<String, Object>>[] repositoryArray);
    void first();
}
