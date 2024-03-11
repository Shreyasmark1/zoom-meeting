package com.example.zoommeeting.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static final String DIRECTORY_DATE_FORMAT = "yyyy_MM_dd";
    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String MYSQL_DB_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_REQUIRED_FOR_MEETING = "yyyy-MM-dd'T'HH:mm:ss";

    public static String getCurrentDate(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return date.format(formatter);
    }

    public static String getCurrentYear(){
        return Year.now().toString();
    }

    public static LocalDateTime mysqlDateTimeToLocalDateTime(String dateTimeString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public static String convertFormat(String value, String format, String newFormat){

        DateTimeFormatter oldFormatter = DateTimeFormatter.ofPattern(format);

        LocalDateTime dateTime = LocalDateTime.parse(value, oldFormatter);

        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern(newFormat);

        return dateTime.format(newFormatter);
    }

    public static String addTimeToMySQLDatetime(String dateStr, int hours, int minutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MYSQL_DB_DATE_FORMAT);
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
        dateTime = dateTime.plus(hours, ChronoUnit.HOURS).plus(minutes, ChronoUnit.MINUTES);
        return dateTime.format(formatter);
    }
}
