package v4;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public interface RepositoryTest {
    Map<String, Object> repository = new HashMap<>();

    static <T> T cast(Class<T> klass, Object o) {
        return klass.cast(o);
    }
}
