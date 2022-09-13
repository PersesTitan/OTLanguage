package work;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface StartWork extends Serializable {
    boolean check(String line);
    void start(String line, String origen, Map<String, Map<String, Object>>[] repositoryArray);
}
