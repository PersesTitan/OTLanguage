package work;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;

public interface StartWork extends Serializable {
    boolean check(String line);
    void start(String line, String origen,
               LinkedList<Map<String, Map<String, Object>>> repositoryArray);
    void first();
}
