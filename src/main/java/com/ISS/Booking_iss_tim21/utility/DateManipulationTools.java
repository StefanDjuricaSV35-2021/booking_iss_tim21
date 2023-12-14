package com.ISS.Booking_iss_tim21.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManipulationTools {
    static String datePattern="dd-MM-yyyy";

    public static Long dateStringToUnix(String date){
        DateFormat dateFormater = new SimpleDateFormat(datePattern);
        Date temp=null;
        try {
            temp=dateFormater.parse(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return temp.getTime()/1000;

    }
}
