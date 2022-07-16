package az.digitalUmbrella.dev.currency.util;

import java.time.LocalDate;

public final class DateUtil {

    private static final Integer SATURDAY = 6;
    private static final Integer SUNDAY = 7;

    public static LocalDate exceptWeekend(LocalDate date) {
        Integer dayOfWeek = date.getDayOfWeek().getValue();

        if (SATURDAY.equals(dayOfWeek)) {
            return date.minusDays(1);
        }

        if (SUNDAY.equals(dayOfWeek)) {
            return date.minusDays(2);
        }

        return date;
    }

    private DateUtil() {
    }

}
