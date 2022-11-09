package bin.time.date;

import bin.exception.MatchException;
import work.v3.ReturnWorkV3;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.LinkedList;
import java.util.Map;

public class DateFormat extends ReturnWorkV3 {
    // 2
    public DateFormat(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                        LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // Date : 2001-11-11
        // [Date][Pattern]
        try {
            return LocalDate.parse(params[0]).format(DateTimeFormatter.ofPattern(params[1]));
        } catch (UnsupportedTemporalTypeException e) {
            throw new MatchException().dateFormatError();
        }
    }
}
