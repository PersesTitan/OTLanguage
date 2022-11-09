package bin.time.time;

import bin.exception.MatchException;
import work.v3.ReturnWorkV3;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.LinkedList;
import java.util.Map;

public class TimeFormat extends ReturnWorkV3 {
    // 2
    public TimeFormat(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // 21:19:41.090381
        // [time, format]
        try {
            return LocalTime.parse(params[0]).format(DateTimeFormatter.ofPattern(params[1]));
        } catch (UnsupportedTemporalTypeException e) {
            throw new MatchException().timeFormatError();
        }
    }
}
