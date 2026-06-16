package util;

import java.time.LocalDate;

public class DateUtil {
    public static String getCurrentDate() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        if (month > 3) {
            year = year - 621;
            month = month - 3;
        } else {
            year = year - 622;
            month = month + 9;
        }

        return String.format("%04d/%02d/%02d", year, month, day);
    }
}