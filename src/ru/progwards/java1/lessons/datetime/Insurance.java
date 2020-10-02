package ru.progwards.java1.lessons.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.spi.TimeZoneNameProvider;

public class Insurance {
    public static enum FormatStyle {SHORT, LONG, FULL}
    private ZonedDateTime start;
    private Duration duration;
    public Insurance(ZonedDateTime start){
        this.start = start;
    }
    public Insurance(String strStart, FormatStyle style){
        switch (style){
            case SHORT:{
                DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
                start = LocalDate.from(dtf.parse(strStart)).atStartOfDay(ZoneId.systemDefault());
                break;
            }
            case FULL: {
                DateTimeFormatter dtf = DateTimeFormatter.ISO_ZONED_DATE_TIME;
                start = ZonedDateTime.from(dtf.parse(strStart));
                break;
            }
            case LONG: {
                DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                start = LocalDateTime.from(dtf.parse(strStart)).atZone(ZoneId.systemDefault());
                break;
            }
        }
    }
    public void setDuration(Duration duration){
        this.duration=duration;
    }
    public void setDuration(ZonedDateTime expiration){
        duration = Duration.between(start,expiration);
    }
    public void setDuration(int months, int days, int hours){
        ZonedDateTime zdt = start.plusMonths(months).plusDays(days).plusHours(hours);
        duration = Duration.between(start,zdt);
    }
    public void setDuration(String strDuration, FormatStyle style){
        switch (style){
            case LONG: {
                DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                LocalDateTime ldt = LocalDateTime.from(dtf.parse(strDuration));
                duration = Duration.between(LocalDateTime.of(0,1,1,0,0),ldt.plusMonths(1));
                break;
            }
            case FULL:{
                duration = Duration.parse(strDuration);
                break;
            }
            case SHORT:{
                duration = Duration.ofMillis(Long.parseLong(strDuration));
                break;
            }
        }
    }
    public boolean checkValid(ZonedDateTime dateTime){
        if (duration!=null)
        return !dateTime.isAfter(start.plus(duration));
        else if (dateTime.isBefore(start))
            return false;
        else return true;
    }
    public String toString(){
        if (ZonedDateTime.now().isBefore(start))
            return "Insurance issued on " + start + " is not valid";
        else if (duration==null)
            return "Insurance issued on " + start + " is valid";
        if(ZonedDateTime.now().isAfter(start.plus(duration)))
            return "Insurance issued on " + start + " is not valid";
        else {
           return  "Insurance issued on " + start + " is valid";
        }
    }

    public static void main(String[] args) {
      ZonedDateTime zdt = ZonedDateTime.parse("2020-09-02T16:42:12.587618+03:00[Europe/Moscow]");
      Insurance insurance = new Insurance(zdt);
      insurance.setDuration(0, 2, 0);
        System.out.println(insurance.toString());


    }
}
