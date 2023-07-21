package com.eric.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    // parse date string in format yyyy-MM-dd to date object
    public static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateStr);
    }

    // format date object to string in format yyyy-MM-dd
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    // check if string is in format yyyy-MM-dd
    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // format date object to string in format yyyyMMdd
    public static String formatDateToyyyyMMdd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    // method convert Date to LocalDate
    public static java.time.LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date convertToDateViaInstant(java.time.LocalDate dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atStartOfDay()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toInstant());
    }
    
    
}
