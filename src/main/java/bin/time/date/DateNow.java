package bin.time.date;

import work.v3.ReturnWorkV3;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

public class DateNow extends ReturnWorkV3 {
    // 0
    public DateNow(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // 2000-01-01
        return LocalDate.now().toString();
    }
}
