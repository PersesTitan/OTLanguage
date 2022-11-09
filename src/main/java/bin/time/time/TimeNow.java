package bin.time.time;

import work.v3.ReturnWorkV3;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Map;

public class TimeNow extends ReturnWorkV3 {
    // 0
    public TimeNow(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // 21:19:41.090381
        return LocalTime.now().toString();
    }
}
