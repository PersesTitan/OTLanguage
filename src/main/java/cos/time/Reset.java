package cos.time;

import bin.apply.Repository;
import lombok.Getter;
import work.ResetWork;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Reset implements ResetWork, Repository, TimeToken {
    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void reset() {
        create(DATE_TIME, DTItem.class, v -> new DTItem());
        create(DATE_TIME, DTItem.class, v -> new DTItem((DateItem) v[0], (TimeItem) v[1]), DATE, TIME);
        create(DATE_TIME, DTItem.class, v -> new DTItem(
                (int) v[0], (int) v[1], (int) v[2], (int) v[3], (int) v[4], (int) v[5], (int) v[6]),
                i, i, i, i, i, i, i);

        create(DATE, DateItem.class, v -> new DateItem());
        create(DATE, DateItem.class, v -> new DateItem((int) v[0], (int) v[1], (int) v[2]), i, i, i);

        create(TIME, TimeItem.class, v -> new TimeItem());
        create(TIME, TimeItem.class, v -> new TimeItem((int) v[0], (int) v[1]), i, i);
        create(TIME, TimeItem.class, v -> new TimeItem((int) v[0], (int) v[1], (int) v[2]), i, i, i);
        create(TIME, TimeItem.class, v -> new TimeItem((int) v[0], (int) v[1], (int) v[2], (int) v[3]), i, i, i, i);

        List.of(
                new Items(NANO, ChronoUnit.NANOS, ChronoField.NANO_OF_SECOND),
                new Items(SECOND, ChronoUnit.SECONDS, ChronoField.SECOND_OF_MINUTE),
                new Items(MINUTE, ChronoUnit.MINUTES, ChronoField.MINUTE_OF_HOUR),
                new Items(HOUR, ChronoUnit.HOURS, ChronoField.HOUR_OF_DAY)
        ).forEach(v -> {
            methodWorks.<TimeItem, Long, TimeItem>add(TIME, v.getPlus(), l, (a, b) -> a.plus(v.getUnit(), b), TIME);
            methodWorks.<TimeItem, Long, TimeItem>add(TIME, v.getMinus(), l, (a, b) -> a.minus(v.getUnit(), b), TIME);
            methodWorks.<TimeItem>add(TIME, v.getGet(), a -> a.get(v.getField()));
            methodWorks.<DTItem, Long, DTItem>add(DATE_TIME, v.getPlus(), l, (a, b) -> a.plus(v.getUnit(), b), DATE_TIME);
            methodWorks.<DTItem, Long, DTItem>add(DATE_TIME, v.getMinus(), l, (a, b) -> a.minus(v.getUnit(), b), DATE_TIME);
            methodWorks.<DTItem>add(DATE_TIME, v.getGet(), a -> a.get(v.getField()));
        });

        List.of(
                new Items(DAY, ChronoUnit.DAYS, ChronoField.DAY_OF_MONTH),
                new Items(WEEK, ChronoUnit.WEEKS, ChronoField.DAY_OF_WEEK),
                new Items(MONTH, ChronoUnit.MONTHS, ChronoField.MONTH_OF_YEAR),
                new Items(YEAR, ChronoUnit.YEARS, ChronoField.YEAR)
        ).forEach(v -> {
            methodWorks.<DateItem, Long, DateItem>add(DATE, v.getPlus(), l, (a, b) -> a.plus(v.getUnit(), b), DATE);
            methodWorks.<DateItem, Long, DateItem>add(DATE, v.getMinus(), l, (a, b) -> a.minus(v.getUnit(), b), DATE);
            methodWorks.<DateItem>add(DATE, v.getGet(), a -> a.get(v.getField()));
            methodWorks.<DTItem, Long, DTItem>add(DATE_TIME, v.getPlus(), l, (a, b) -> a.plus(v.getUnit(), b), DATE_TIME);
            methodWorks.<DTItem, Long, DTItem>add(DATE_TIME, v.getMinus(), l, (a, b) -> a.minus(v.getUnit(), b), DATE_TIME);
            methodWorks.<DTItem>add(DATE_TIME, v.getGet(), a -> a.get(v.getField()));
        });
    }

    @Getter
    private static class Items {
        private final ChronoUnit unit;
        private final ChronoField field;
        private final String plus, minus, get;
        private Items(String name, ChronoUnit unit, ChronoField field) {
            this.unit = unit;
            this.field = field;
            this.plus = "+".concat(name);
            this.minus = "-".concat(name);
            this.get = ">".concat(name);
        }
    }
}
