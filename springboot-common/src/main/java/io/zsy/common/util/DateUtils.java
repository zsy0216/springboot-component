package io.zsy.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author zhangshuaiyin
 * @date 2021-05-27 08:59
 */
public final class DateUtils {


    private final static DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ISO_LOCAL_DATE;
    private final static DateTimeFormatter ISO_LOCAL_TIME = DateTimeFormatter.ISO_LOCAL_TIME;
    private final static DateTimeFormatter ISO_TIME = DateTimeFormatter.ISO_TIME;
    private final static DateTimeFormatter ISO_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * LocalDateTime -> Date
     * LocalDate/LocalDateTime -> ZonedDateTime -> Instant -> Date
     *
     * @param localDate LocalDate/localDateTime
     * @return Date
     */

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date -> LocalDate
     * Date -> Instant -> ZonedDateTime -> LocalDate/LocalDateTime
     *
     * @param date date
     * @return LocalDate/LocalDateTime
     */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static long toCurrentTimeMillis(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static Instant toInstant(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.format(YYYY_MM_DD));
        System.out.println(toInstant(localDateTime));
        System.out.println(toCurrentTimeMillis(localDateTime));
        System.out.println(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        System.out.println(localDateTime.toEpochSecond(ZoneOffset.ofHours(8)));
        System.out.println(System.currentTimeMillis());
    }
}
