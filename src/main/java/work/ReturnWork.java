package work;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;

public interface ReturnWork extends Serializable {
    boolean check(String line);
    String start(String line, LinkedList<Map<String, Map<String, Object>>> repositoryArray);
    ReturnWork first();
}
