package ru.progwards.java1.lessons.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;

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
        ZonedDateTime zdt = start.plusMonths(months);
        zdt.plusDays(days);
        zdt.plusHours(hours);
        duration = Duration.between(start,zdt);
    }
    public void setDuration(String strDuration, FormatStyle style){
        switch (style){
            case LONG: {
                DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                duration = Duration.between(start,start.minusSeconds(LocalDateTime.from(dtf.parse(strDuration)).atZone(ZoneId.systemDefault()).getSecond()));
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
        ZonedDateTime zdt = Instant.now().atZone(ZoneId.systemDefault());
        String s ="2011-12-03";
        Insurance insurance = new Insurance(s,FormatStyle.SHORT);
        Duration duration = Duration.parse("PT27H46M40S");
        String s1= "PT27H46M40S";
        insurance.setDuration(s1,FormatStyle.FULL);
        System.out.println(insurance.duration);


    }
}
