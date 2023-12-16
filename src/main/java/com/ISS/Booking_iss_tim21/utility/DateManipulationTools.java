package com.ISS.Booking_iss_tim21.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateManipulationTools {
    static String datePattern="yyyy-MM-dd";

    public static Long dateStringToUnix(String date){
        DateFormat dateFormater = new SimpleDateFormat(datePattern);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date temp=null;
        try {
            temp=dateFormater.parse(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return temp.getTime()/1000;

    }
}
