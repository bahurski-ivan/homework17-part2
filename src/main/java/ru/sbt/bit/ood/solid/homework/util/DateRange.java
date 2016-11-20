package ru.sbt.bit.ood.solid.homework.util;

import java.time.LocalDate;

/**
 * Created by Ivan on 20/11/2016.
 */
public class DateRange {
    private final LocalDate from;
    private final LocalDate to;

    private DateRange(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public static DateRange of(LocalDate from, LocalDate to) {
        return new DateRange(from, to);
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
