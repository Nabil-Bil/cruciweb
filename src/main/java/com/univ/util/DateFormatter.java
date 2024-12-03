package com.univ.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter {


    public static String formatDate(Date date) {
        if (isToday(date)) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            return timeFormat.format(date);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        }
    }

    private static boolean isToday(Date date) {
        Calendar today = Calendar.getInstance();
        Calendar inputDate = Calendar.getInstance();
        inputDate.setTime(date);

        return today.get(Calendar.YEAR) == inputDate.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == inputDate.get(Calendar.DAY_OF_YEAR);
    }
    
}