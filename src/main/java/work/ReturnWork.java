package work;

import java.io.Serializable;
import java.util.Map;

public interface ReturnWork extends Serializable {
    boolean check(String line);
    String start(String line, Map<String, Map<String, Object>>[] repositoryArray);
    ReturnWork first();
}
