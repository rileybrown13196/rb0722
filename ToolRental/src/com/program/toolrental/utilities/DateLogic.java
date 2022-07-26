package com.program.toolrental.utilities;

public class DateLogic {
    public static boolean isWeekday(int dayOfWeek) {
        boolean isWeekday;
        if ((!(dayOfWeek == 1) && (!(dayOfWeek == 7)))) {
            isWeekday = true;
        } else {
            isWeekday = false;
        }
        return isWeekday;
    }

    public static boolean isWeekend(int dayOfWeek) {
        if ((dayOfWeek == 1) || (dayOfWeek == 7)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isHoliday(int monthAsInt, int dayOfMonthAsInt, int dayOfWeek) {
        boolean holiday = false;

        boolean julyFourth = isJulyFourth(monthAsInt, dayOfMonthAsInt, dayOfWeek);
        boolean laborDay = isLaborDay(monthAsInt, dayOfMonthAsInt, dayOfWeek);

        if (julyFourth || laborDay) {
            holiday = true;
        }
        return holiday;
    }

    public static boolean isLaborDay(int month, int dayOfMonth, int dayOfWeek) {
        boolean laborDay = false;
        if ((month == 8) && (dayOfWeek == 2)) {
            int adjustedDate = dayOfMonth - 7;
            if (adjustedDate < 1) {
                laborDay = true;
            }
        }
        return laborDay;
    }

    public static boolean isJulyFourth(int month, int dayOfMonth, int dayOfWeek) {
        boolean julyFourth = false;

        int nextDayOfWeek = dayOfWeek + 1;
        int nextDayOfMonth = dayOfMonth + 1;
        int previousDayOfWeek = dayOfWeek - 1;
        int previousDayOfMonth = dayOfMonth - 1;

        // If it is a weekday, and it is the fourth of july, the holiday rate is applied that day.
        if (dayOfWeek != 1 && dayOfWeek != 7) {
            if ((month == 6) && (dayOfMonth == 4)) {
                julyFourth = true;
            }
        }

        if(dayOfWeek == 6){
            // If the next day is saturday, and it equals july 4th, Holiday rate is charged today(Friday).
            if ((nextDayOfWeek == 7) && (month == 6) && (nextDayOfMonth == 4)) {
                julyFourth = true;
            }
        }

        if(dayOfWeek == 2){
            // If the day before was sunday, and it was the fourth of july, Holiday rate is charged for today(Monday).
            if ((previousDayOfWeek == 1) && (month == 6) && (previousDayOfMonth == 4)) {
                julyFourth = true;
            }
        }

        return julyFourth;
    }
}
