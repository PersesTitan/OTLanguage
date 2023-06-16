package cos.time;

import bin.apply.item.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@Getter
@RequiredArgsConstructor
class DTItem implements Item {
    private final LocalDateTime dt;

    public DTItem() {
        this.dt = LocalDateTime.now();
    }

    public DTItem(DateItem date, TimeItem time) {
        this.dt = LocalDateTime.of(date.getDate(), time.getTime());
    }

    public DTItem(int year, int month, int day, int hour, int minus, int second, int nano) {
        TimeToken.checkValue(month, 1, 12);
        TimeToken.checkValue(day, 1, 31);
        TimeToken.checkValue(hour, 0, 23);
        TimeToken.checkValue(minus, 0, 59);
        TimeToken.checkValue(second, 0, 59);
        this.dt = LocalDateTime.of(year, month, day, hour, minus, second, nano);
    }

    public DTItem plus(ChronoUnit unit, long time) {
        return new DTItem(this.dt.plus(time, unit));
    }

    public DTItem minus(ChronoUnit unit, long time) {
        return new DTItem(this.dt.minus(time, unit));
    }

    public int get(ChronoField chronoField) {
        return this.dt.get(chronoField);
    }

    @Override
    public String toString() {
        int year = dt.getYear();
        int month = dt.getMonthValue();
        int day = dt.getDayOfMonth();
        int hour = dt.getHour();
        int minus = dt.getMinute();
        int second = dt.getSecond();
        int nano = dt.getNano();
        return this.itemToString(TimeToken.DATE_TIME, year, month, day, hour, minus, second, nano);
    }
}
