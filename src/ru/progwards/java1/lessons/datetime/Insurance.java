package ru.progwards.java1.lessons.datetime;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Insurance {
    public static enum FormatStyle {SHORT, LONG, FULL}
    private ZonedDateTime start;
    private Duration duration;
    public Insurance(ZonedDateTime start){
        this.start = start;
    }
    public Insurance(String strStart, FormatStyle style){
        DateTimeFormatter dtf=null;
        switch (style){
            case SHORT:
                dtf = DateTimeFormatter.ISO_LOCAL_DATE;
                break;
            case FULL:
                dtf = DateTimeFormatter.ISO_ZONED_DATE_TIME;
                break;
            case LONG:
                dtf = DateTimeFormatter. ISO_LOCAL_DATE_TIME;
                break;
        }
        start = ZonedDateTime.from(dtf.parse(strStart));
    }
    public void setDuration(Duration duration){
        this.duration=duration;
    }
    public void setDuration(ZonedDateTime expiration){
        duration = Duration.between(start,expiration);
    }
    public void setDuration(int months, int days, int hours){
        ZonedDateTime zdt = start.plusMonths(months);
        zdt.plusDays(days);
        zdt.plusHours(hours);
        duration = Duration.between(start,zdt);
    }
    public void setDuration(String strDuration, FormatStyle style){
        switch (style){
            case LONG: {
                DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                duration = Duration.between(start,ZonedDateTime.from(dtf.parse(strDuration)));
            }
            case FULL:{
                duration = Duration.parse(strDuration);
            }
            case SHORT:{
                duration = Duration.ofMillis(Long.parseLong(strDuration));
            }
        }
    }
    public boolean checkValid(ZonedDateTime dateTime){
        if (duration!=null)
        return !dateTime.isAfter(start.plus(duration));
        else return true;
    }
    public String toString(){
        if (duration==null)
            return "Insurance issued on " + start + " is valid";
        if(ZonedDateTime.now().isAfter(start.plus(duration)))
            return "Insurance issued on " + start + " is not valid";
        else {
           return  "Insurance issued on " + start + " is valid";
        }
    }

}
