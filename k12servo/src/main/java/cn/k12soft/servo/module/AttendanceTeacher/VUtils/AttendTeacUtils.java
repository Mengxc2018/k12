package cn.k12soft.servo.module.AttendanceTeacher.VUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.TimeZone;

public class AttendTeacUtils {

    /**
     * 获取上个月
     * @param localDate
     * @return
     */
    public static Long beforeOneMonth(LocalDate localDate){
        Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(instant.toEpochMilli());
        cal.add(Calendar.MONTH,   -1);
        String yesterdayStr = new SimpleDateFormat( "yyyyMMdd").format(cal.getTime());
        Long date = new Long((String)yesterdayStr);
        return date;
    }

    /**
     * 获取下个月
     * @param localDate
     * @return
     */
    public static Long afterOneMonth(LocalDate localDate){
        Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(instant.toEpochMilli());
        cal.add(Calendar.MONTH,   +1);
        String yesterdayStr = new SimpleDateFormat( "yyyyMMdd").format(cal.getTime());
        Long date = new Long((String)yesterdayStr);
        return date;
    }

}
