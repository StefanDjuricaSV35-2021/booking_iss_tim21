package com.ISS.Booking_iss_tim21.utility;

import com.ISS.Booking_iss_tim21.model.TimeSlot;
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

        return temp.getTime()/1000;

    }

    public static String UnixToString(Long date){
        DateFormat dateFormatter = new SimpleDateFormat(datePattern);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date temp= new Date((date*1000));

        return dateFormatter.format(temp);

    }



    public static List<String> datesFromTimeSlots(List<TimeSlot> timeSlots){

        List<String> dates=new ArrayList<>();

        for(TimeSlot ts:timeSlots){
            dates.addAll(getDatesBetween(UnixToString(ts.getStartDate()),UnixToString(ts.getEndDate())));
        }

        return dates;
    }

    public static List<String> getDatesBetween(String startDate, String endDate){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

        LocalDate date1 = LocalDate.parse(startDate, dateFormatter);
        LocalDate date2 = LocalDate.parse(endDate, dateFormatter);

        List<LocalDate> datesBetween=date1.datesUntil(date2).toList();

        return datesToStrings(datesBetween);

    }

    static List<String> datesToStrings(List<LocalDate> dates){

        List<String> convertedDates=new ArrayList<>();

        for (LocalDate ld:dates){

            convertedDates.add(ld.format(DateTimeFormatter.ofPattern(datePattern)));
        }

        return convertedDates;

    }
}
