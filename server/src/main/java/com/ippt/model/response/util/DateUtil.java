package com.ippt.model.response.util;

import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public final class DateUtil {
    private DateUtil() { }

    public static Optional<String> to12HourFormat(LocalTime time) {
        return Optional.ofNullable(time).map(t -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            return t.format(formatter);
        });
    }

    public static Optional<String> toStringFormat(ChronoLocalDate airDate) {
        return Optional.ofNullable(airDate).map(ChronoLocalDate::toString);
    }
}
