package cn.k12soft.servo.util;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.springframework.data.util.Pair;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class Times {
    public static final long ONE_YEAR_MILLIS = 365*24*3600*1000;// 一年
    public static final long ONE_MONTH_MILLIS = 30*24*3600*1000;// 一个月
    public static final long ONE_WEEK_MILLIS = 7*24*3600*1000L;// 一周的毫秒数
    public static final long ONE_DAY_MILLIS = 24*3600*1000L;// 一天的毫秒数

    private final static Set<Integer> LAST_TERM_MONTH = new HashSet<>(); // 上学期的月份
    static{
        LAST_TERM_MONTH.add(3);
        LAST_TERM_MONTH.add(4);
        LAST_TERM_MONTH.add(5);
        LAST_TERM_MONTH.add(6);
        LAST_TERM_MONTH.add(7);
        LAST_TERM_MONTH.add(8);
    }

    public static int currentSeconds() {
    return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
  }

  public static Timestamp currentTimestamp() {
    return Timestamp.from(Instant.now());
  }

  private Times() {
  }

  /**
   * 当月第一天零点和下个月第一天零点(eg: [2017-12-01 00:00:00, 2018-01-01 00:00:00])
   */
  public static long[] getBeginAndEndOfCurrentMonth() {
    long[] time = new long[2];
    LocalDate today = LocalDate.now();
    LocalDate firstday = LocalDate.of(today.getYear(), today.getMonth(), 1);
    LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());
    Timestamp firstTm = Timestamp.valueOf(firstday.atStartOfDay());
    Timestamp lastTm = Timestamp.valueOf(lastDay.atStartOfDay());
    time[0] = firstTm.getTime();
    time[1] = lastTm.getTime() + 24 * 3600 * 1000l;
    return time;
  }

  /**
   * 当天零点(eg: 2017-12-08 00:00:00)
   */
  public static long getZeroTimeOfToday() {
    LocalDateTime zeroDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
    return Timestamp.valueOf(zeroDateTime).getTime();
  }

  public static long monthStartTime(long time){
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTimeInMillis();
  }

  public static long monthEndTime(long time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);
    calendar.add(Calendar.MONTH, 1);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTimeInMillis();
  }

  public static int time2yyyyMMdd(long time){
      SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
      return Integer.valueOf(sd.format(time));
  }

    public static int time2yyyyMM(long periodDateTime) {
      SimpleDateFormat sd = new SimpleDateFormat("yyyyMM");
      return Integer.valueOf(sd.format(periodDateTime));
    }

    public static long yyyyMM2Date(int yyyyMM) {
      SimpleDateFormat sd = new SimpleDateFormat("yyyyMM");
        try {
            return sd.parse(String.valueOf(yyyyMM)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回yyyy年1月(eg: 201801)
     * @param time
     * @return
     */
    public static int getYear(long time){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        return time2yyyyMM(c.getTimeInMillis());
    }

    /**
     * 返回yyyy年上半年第一个月分，下半年第一个月份(201801, 201807)
     * @param time
     * @return
     */
    public static int getHalfYear(long time){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            default:
                break;
        }
        return time2yyyyMM(c.getTimeInMillis());
    }

    /**
     *
     * 返回季度xxxx年每个季度第一个月(eg: 201801, 201804, 201807, 201810);
     *
     * @return
     */
    public static int getSeason(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            default:
                break;
        }
        return time2yyyyMM(c.getTimeInMillis());
    }

    public static void main(String[] args){
        long time = 1490977436000l;
        int season = getSeason(time);

        Instant now = Instant.now();
        Instant future = now.plus(24, ChronoUnit.HOURS);
        System.out.println(getIntervalDays(future, now));
    }

    public static Pair<LocalDate,LocalDate> getTermDatePair(LocalDate localDateNow) {
        Month currMonth = localDateNow.getMonth();
        if(LAST_TERM_MONTH.contains(currMonth.getValue())){
            LocalDate fromDate = localDateNow.with(TemporalAdjusters.firstDayOfYear()).plusMonths(2);
            LocalDate toDate = fromDate.plusMonths(6);
            return Pair.of(fromDate, toDate);
        }else{
            LocalDate fromDate = localDateNow.with(TemporalAdjusters.firstDayOfYear()).plusMonths(8);
            LocalDate toDate = fromDate.plusYears(1).with(TemporalAdjusters.firstDayOfYear()).plusMonths(1);
            return Pair.of(fromDate, toDate);
        }
    }

    public static int getIntervalDays(Instant toDate, Instant fromDate) {
        return (int)fromDate.until(toDate, ChronoUnit.DAYS);
    }

    public static LocalDate long2LocalDate(long time) {
        Date date = new Date(time);
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 2014-12-04 19:24:29
     * @param time
     * @return
     */
    public static String fromTimeToStandardStr(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return sdf.format(calendar.getTime());
    }

    /**
     * 2014-12-04 19:24:29
     * @param time
     * @return
     */
    public static String fromTimeToyyyyMMddHHmmSS(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return sdf.format(calendar.getTime());
    }

    /**
     * 判断两个时间戳(Timestamp)是否在同一天
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isTheSameDate(long time1, long time2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(time1);
        int y1 = c1.get(Calendar.YEAR);
        int m1 = c1.get(Calendar.MONTH);
        int d1 = c1.get(Calendar.DATE);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(time2);
        int y2 = c2.get(Calendar.YEAR);
        int m2 = c2.get(Calendar.MONTH);
        int d2 = c2.get(Calendar.DATE);
        if (y1 == y2 && m1 == m2 && d1 == d2) {
            return true;
        }
        return false;
    }

    /*
    * 计算两个时间戳间隔几天
    * */
    public static int getDayInterval(long time1, long time2){
        long zeroTime1 = getZeroClock(time1);
        long zeroTime2 = getZeroClock(time2);
        long interval = Math.abs(zeroTime2 - zeroTime1);
        int dayInterval = (int)(interval/86400000);
        return dayInterval;
    }

    public static long getZeroClock(long dateInMillis){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateInMillis);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return  cal.getTimeInMillis();
    }

    public static Pair<Instant, Instant> getFirstAndSecond(LocalDate localDate){
        Instant first = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);
        return Pair.of(first, second);
    }
}
