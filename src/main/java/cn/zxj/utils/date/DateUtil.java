package cn.zxj.utils.date;
import org.apache.commons.lang.time.DateFormatUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类.
 */
public class DateUtil {

    /**
     * 年月日格式.
     */
    public final static String YYYY_MM_DD_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 年月日格式.
     */
    public final static String YYYY_MM_DD_PATTERN2 = "yyyy/MM/dd";
    /**
     * xxxx年xx月xx日 格式
     */
    public final static String YYYY_MM_DD_PATTERN3 = "yyyy年MM月dd日";
    /**
     * 年月日时分秒格式.
     */
    public final static String YYYY_MM_DD_HH_MM_SS_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static String YYYYMMDDHHMMSS_PATTERN = "yyyyMMddHHmmss";

    /**
     * 构造方法.
     */
    private DateUtil() {

    }

    /**
     * 得到格式化的时间.
     *
     * @param time
     *            日期
     * @param pattern
     *            格式
     * @return String 格式化的时间
     */
    public static String getFormatTime(Date time, String pattern) {
        try {
            return DateFormatUtils.format(time, pattern);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到格式化后的当前时间.
     *
     * @param pattern
     *            格式
     * @return 格式化后的当前时间
     */
    public static String getFormatNowTime(String pattern) {
        try {
            Date date = new Date();
            return getFormatTime(date, pattern);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到格式化后的当前时间几月前的时间.
     *
     * @param monthsAgo
     *            前几个月
     * @param pattern
     *            格式
     * @return String 格式化后的当前时间几月前的时间
     */
    public static String getFormatTimeBeforeMonthTime(int monthsAgo, String pattern) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -monthsAgo);

            return getFormatTime(calendar.getTime(), pattern);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到当前时间几月前的时间.
     *
     * @param monthsAgo
     *            前几个月
     * @return Date 当前时间几月前的时间
     */
    public static Date getTimeBeforeMonthTime(int monthsAgo) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -monthsAgo);

            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到格式化后的当前时间几天前的时间.
     *
     * @param daysAgo
     *            几天前
     * @param pattern
     *            格式
     * @return String 格式化后的当前时间几天前的时间
     */
    public static String getFormatTimeBeforeDaysTime(int daysAgo, String pattern) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -daysAgo);
            return getFormatTime(calendar.getTime(), pattern);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据传入的年/月/日/时/分/秒/毫秒 得到目标时间. 其中如果传入为null 则这个值取当前时间的年，月等的值
     *
     * @param year
     *            year
     * @param month
     *            month
     * @param day
     *            day
     * @param hour
     *            hour
     * @param minute
     *            minute
     * @param second
     *            second
     * @param millSecond
     *            millSecond
     * @return Date 根据传入的年/月/日/时/分/秒/毫秒 得到目标时间
     */
    public static Date getTargetTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second,
                                     Integer millSecond) {

        Calendar calendar = Calendar.getInstance();
        if (year != null)
            calendar.set(Calendar.YEAR, year);

        if (month != null)
            calendar.set(Calendar.MONTH, (month - 1));

        if (day != null)
            calendar.set(Calendar.DAY_OF_MONTH, day);

        if (hour != null)
            calendar.set(Calendar.HOUR_OF_DAY, hour);

        if (minute != null)
            calendar.set(Calendar.MINUTE, minute);

        if (second != null)
            calendar.set(Calendar.SECOND, second);

        if (millSecond != null)
            calendar.set(Calendar.MILLISECOND, millSecond);
        return calendar.getTime();

    }

    /**
     * 根据传入的年/月/日/时/分/秒/毫秒 得到目标时间. 其中如果传入为null 则这个值取当前时间的年，月等的值
     *
     * @param addyear
     *            addyear
     * @param addmonth
     *            addmonth
     * @param addday
     *            addday
     * @param addhour
     *            addhour
     * @param addminute
     *            addminute
     * @param addsecond
     *            addsecond
     * @param addmillSecond
     *            addmillSecond
     * @return Date 根据传入的年/月/日/时/分/秒/毫秒 得到目标时间
     */
    public static Date getAddTargetTime(Integer addyear, Integer addmonth, Integer addday, Integer addhour, Integer addminute,
                                        Integer addsecond, Integer addmillSecond) {

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millSecond = calendar.get(Calendar.MILLISECOND);

        if (addyear != null) {
            calendar.set(Calendar.YEAR, year + addyear);
        }

        if (addmonth != null)
            calendar.set(Calendar.MONTH, month + addmonth);

        if (addday != null)
            calendar.set(Calendar.DAY_OF_MONTH, day + addday);

        if (addhour != null)
            calendar.set(Calendar.HOUR_OF_DAY, hour + addhour);

        if (addminute != null)
            calendar.set(Calendar.MINUTE, minute + addminute);

        if (addsecond != null)
            calendar.set(Calendar.SECOND, second + addsecond);

        if (addmillSecond != null)
            calendar.set(Calendar.MILLISECOND, millSecond + addmillSecond);
        return calendar.getTime();

    }

    /**
     * @return 获取当前月的总天数
     */
    public static int getDaysOfThisMonth() {
        Calendar now = Calendar.getInstance();
        // 把日期设置为当月第一天
        now.set(Calendar.DATE, 1);
        // 日期回滚一天，也就是最后一天
        now.roll(Calendar.DATE, -1);
        int maxDate = now.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 得到清零时间的日期.
     *
     * @param date
     *            date
     * @return Date 清零时间的日期
     */
    public static Date getClearingDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();

    }

    /**
     * 调整时间.
     * <p/>
     * <ul>
     * <li>保留时间的时分秒纳秒部分</li>
     * <li>将时间的年月日转换成今天的年月日</li>
     * <li>如果时大于19，则时为9</li>
     * <li>如果时小于9，则时为9</li>
     * <li>例如时间2009-08-12 22:23:16 ,今天为2010-10-06
     * <li/>
     * <li>转换后为:2010-10-06 09:23:16</li>
     * </ul>
     *
     * @param date
     * @return 调整后的日期时间
     */
    public static Date getHMSDate(Date date) {
        // 如果没有传时间过来，则10点触发
        if (date == null) {
            return DateUtil.getTargetTime(null, null, null, 10, null, null, null);
        }
        int hour = DateUtil.getHour(date);
        int minute = DateUtil.getMinute(date);
        int second = DateUtil.getSecond(date);
        int millSecond = DateUtil.getMillSecond(date);
        // 早上九点之前的设置为九点，晚上九点之后的设置为九点
        if (hour < 9 || hour > 19) {
            hour = 9;
        }
        return DateUtil.getTargetTime(null, null, null, hour, minute, second, millSecond);
    }

    /**
     * 得到年.
     *
     * @param date
     *            date
     * @return int 得到年
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 得到月.
     *
     * @param date
     *            date
     * @return int 得到月
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到天.
     *
     * @param date
     *            date
     * @return int int
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到时.
     *
     * @param date
     *            date
     * @return int int
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 得到分.
     *
     * @param date
     *            date
     * @return int int
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 得到秒.
     *
     * @param date
     *            date
     * @return int int
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 得到纳秒.
     *
     * @param date
     *            date
     * @return int int
     */
    public static int getMillSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MILLISECOND);
    }

    /**
     * 判断是否是月底.
     *
     * @param date
     *            date
     * @return int int
     */
    public static boolean isEOM(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now = c.get(Calendar.DAY_OF_MONTH);
        return lastDay == now;
    }

    /**
     * 计算两个日期之间的天数差
     *
     * @param begin
     * @param end
     * @return
     */
    public static Integer distanceDate(final Date begin, final Date end) {
        Calendar ca1 = Calendar.getInstance();
        Calendar ca2 = Calendar.getInstance();
        ca1.setTime(begin);
        ca2.setTime(end);
        long time1 = ca1.getTimeInMillis();
        long time2 = ca2.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间的周数差
     *
     * @param begin
     * @param end
     * @return
     */
    public static Integer distanceWeek(final Date begin, final Date end) {
        Calendar ca1 = Calendar.getInstance();
        Calendar ca2 = Calendar.getInstance();
        ca1.setTime(begin);
        ca2.setTime(end);
        long time1 = ca1.getTimeInMillis();
        long time2 = ca2.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24 * 7);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间的月数差
     *
     * @param begin
     * @param end
     * @return
     */
    public static Integer distanceMonth(final Date begin, final Date end) {
        Calendar ca1 = Calendar.getInstance();
        Calendar ca2 = Calendar.getInstance();
        ca1.setTime(begin);
        ca2.setTime(end);
        int result = 0;
        result = ca2.get(Calendar.MONTH) - ca1.get(Calendar.MONTH);
        return result == 0 ? 1 : Math.abs(result);
    }

    /**
     * 计算两个日期之间的月数差：如果时间在一个月内，则返回1，否则返回相差月份
     *
     * @param begin
     * @param end
     * @return
     */
    public static Integer distanceMonths(final Date begin, final Date end) {
        Calendar ca1 = Calendar.getInstance();
        Calendar ca2 = Calendar.getInstance();
        ca1.setTime(begin);
        ca2.setTime(end);
        int resultYear = 0;
        resultYear = ca2.get(Calendar.YEAR) - ca1.get(Calendar.YEAR);
        int result = 0;
        result = ca2.get(Calendar.MONTH) - ca1.get(Calendar.MONTH);
        result = result + resultYear * 12;
        return result == 1 || result == 0 || result == -1 ? 1 : result;
    }

    /**
     * 计算两个日期之间的月数差
     *
     * @param begin
     * @param end
     * @return
     */
    public static Integer distanceDateMax(final Date begin, final Date end) {
        Calendar ca1 = Calendar.getInstance();
        Calendar ca2 = Calendar.getInstance();
        ca1.setTime(begin);
        ca2.setTime(end);
        Long result = 0L;
        result = ca2.getTimeInMillis() - ca1.getTimeInMillis();
        return result >= 0L ? 1 : 0;
    }

    /**
     * <p>
     * 根据传入的2个时间参数计算时间差.
     * </p>
     *
     * <pre>
     *   根据传入的开始时间<code>begin</code>和结束时间<code>end</code>，
     *   计算出2者的时间差，返回计算的时间差，格式为hh:mi:ss
     * </pre>
     *
     * @param begin
     *            开始时间
     * @param end
     *            结束时间
     * @return String 时间差（格式：hh:mi:ss）
     */
    public static String distanceTime(final Date begin, final Date end) {

        Calendar ca1 = Calendar.getInstance();
        Calendar ca2 = Calendar.getInstance();
        ca1.setTime(begin);
        ca2.setTime(end);

        // 时差
        int distanceHour = ca2.get(Calendar.HOUR_OF_DAY) - ca1.get(Calendar.HOUR_OF_DAY);
        // 分差
        int distanceMin = ca2.get(Calendar.MINUTE) - ca1.get(Calendar.MINUTE);
        // 秒差
        int distanceSed = ca2.get(Calendar.SECOND) - ca1.get(Calendar.SECOND);

        StringBuffer strBuff = new StringBuffer();
        strBuff.append(distanceHour);
        strBuff.append(":");
        strBuff.append(distanceMin);
        strBuff.append(":");
        strBuff.append(distanceSed);
        strBuff.append(":");

        return strBuff.toString();
    }

    /**
     * 得到日期的当前时间.
     * <p/>
     * <ul>
     * <li>例如date=20110505 10:10:10,当前时间是20110531 12:12:12,则返回的结果是20110505
     * 12:12:12</li>
     * <li>保留date的年月日，保留当前时间的时分秒</li>
     * </ul>
     *
     * @param date
     * @return
     */
    public static Date getCurrentTimeOfDate(Date date) {
        Date currentDate = new Date();
        int year = getYear(date);
        int month = getMonth(date);
        int day = getDay(date);
        int hour = getHour(currentDate);
        int minute = getMinute(currentDate);
        int second = getSecond(currentDate);

        return DateUtil.getTargetTime(year, month, day, hour, minute, second, null);
    }

    /**
     * 把字符串转为date对象
     *
     * @param strDate
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static final Date convertStringToDate(String strDate, String pattern) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(pattern);

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            return null;
        }

        return (date);
    }

    /**
     * 把字符串转为date对象,默认采用yyyy-mm-dd HH:MM:SS格式
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static final Date str2Date(String strDate) throws ParseException {
        return convertStringToDate(strDate, YYYY_MM_DD_HH_MM_SS_PATTERN);
    }

    public static Date getMondayOfThisWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return c.getTime();
    }

    public static Date addDays(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    public static Date addWeeks(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_YEAR, days);
        return c.getTime();
    }

    public static Date addMonths(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, days);
        return c.getTime();
    }

    /**
     * 计算两个时间差：返回秒
     *
     * @param begin
     * @param end
     * @return
     */
    public static Long distanceMillis(final Date begin, final Date end) {
        return (end.getTime() - begin.getTime()) / 1000;
    }

    /**
     * 返回两个日期段内的所有日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getDays(Date startDate, Date endDate) {
        List<String> days = new ArrayList<String>();
        while (endDate.after(startDate) || endDate.equals(startDate)) {
            days.add(DateFormatUtils.format(startDate, YYYY_MM_DD_PATTERN));
            startDate.setTime(startDate.getTime() + 24 * 60 * 60 * 1000);
        }
        return days;
    }

    /**
     * 获取日期当前月第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return cal.getTime();
    }

    /**
     * 获取日期当前月最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthLastDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
}

