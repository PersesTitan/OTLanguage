package cos.time;

import bin.apply.item.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@Getter
@RequiredArgsConstructor
class DateItem implements Item {
    private final LocalDate date;

    public DateItem() {
        this.date = LocalDate.now();
    }

    public DateItem(int year, int month, int day) {
        TimeToken.checkValue(month, 1, 12);
        TimeToken.checkValue(day, 1, 31);
        this.date = LocalDate.of(year, month, day);
    }

    DateItem(LocalDateTime dt) {
        int year = dt.getYear();
        int month = dt.getMonthValue();
        int dayOfMonth = dt.getDayOfMonth();
        this.date = LocalDate.of(year, month, dayOfMonth);
    }

    public DateItem plus(ChronoUnit unit, long time) {
        return new DateItem(this.date.plus(time, unit));
    }

    public DateItem minus(ChronoUnit unit, long time) {
        return new DateItem(this.date.minus(time, unit));
    }

    public int get(ChronoField chronoField) {
        return this.date.get(chronoField);
    }

    @Override
    public String toString() {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        return this.itemToString(TimeToken.DATE, year, month, day);
    }
}
