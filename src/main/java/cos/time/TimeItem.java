package cos.time;

import bin.apply.item.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@Getter
@RequiredArgsConstructor
class TimeItem implements Item {
    private final LocalTime time;

    TimeItem(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        int second = dateTime.getSecond();
        int nano = dateTime.getNano();
        this.time = LocalTime.of(hour, minute, second, nano);
    }

    public TimeItem() {
        this.time = LocalTime.now();
    }

    public TimeItem(int hour, int minute) {
        TimeToken.checkValue(hour, 0, 23);
        TimeToken.checkValue(minute, 0, 59);
        this.time = LocalTime.of(hour, minute);
    }

    public TimeItem(int hour, int minute, int second) {
        TimeToken.checkValue(hour, 0, 23);
        TimeToken.checkValue(minute, 0, 59);
        TimeToken.checkValue(second, 0, 59);
        this.time = LocalTime.of(hour, minute, second);
    }

    public TimeItem(int hour, int minute, int second, int nano) {
        TimeToken.checkValue(hour, 0, 23);
        TimeToken.checkValue(minute, 0, 59);
        TimeToken.checkValue(second, 0, 59);
        this.time = LocalTime.of(hour, minute, second, nano);
    }

    public TimeItem plus(ChronoUnit unit, long time) {
        return new TimeItem(this.time.plus(time, unit));
    }

    public TimeItem minus(ChronoUnit unit, long time) {
        return new TimeItem(this.time.minus(time, unit));
    }

    public int get(ChronoField chronoField) {
        return this.time.get(chronoField);
    }

    @Override
    public String toString() {
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        int nano = time.getNano();
        return this.itemToString(TimeToken.TIME, hour, minute, second, nano);
    }
}
