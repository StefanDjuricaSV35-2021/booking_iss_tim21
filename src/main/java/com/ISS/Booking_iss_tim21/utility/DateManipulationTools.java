package com.ISS.Booking_iss_tim21.utility;

import com.ISS.Booking_iss_tim21.config.AppConfig;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import org.joda.time.LocalTime;
import org.springframework.cglib.core.Local;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class DateManipulationTools {
    static String datePattern="yyyy-MM-dd";
    public static Long dateStringToUnix(String date){
        DateFormat dateFormatter = new SimpleDateFormat(datePattern);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date temp=null;
        try {
            temp=dateFormatter.parse(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return temp.getTime()/ AppConfig.UNIX_DIFF;

    }

    public static String UnixToString(Long date){
        DateFormat dateFormatter = new SimpleDateFormat(datePattern);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date temp= new Date((date*AppConfig.UNIX_DIFF));

        return dateFormatter.format(temp);

    }

    public static TimeSlot datesToTimeslot(org.joda.time.LocalDate beginDate, org.joda.time.LocalDate endDate){

        TimeSlot newTs=new TimeSlot();
        newTs.setStartDate(beginDate.toLocalDateTime(LocalTime.MIDNIGHT).toDateTime(AppConfig.gmtTimeZone).toInstant().getMillis()/AppConfig.UNIX_DIFF);
        newTs.setEndDate(endDate.toLocalDateTime(LocalTime.MIDNIGHT).toDateTime(AppConfig.gmtTimeZone).toInstant().getMillis()/AppConfig.UNIX_DIFF);

        return newTs;

    }
}
