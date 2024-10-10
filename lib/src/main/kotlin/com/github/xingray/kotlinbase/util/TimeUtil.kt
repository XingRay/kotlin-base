package com.github.xingray.kotlinbase.util

import java.time.*
import java.time.format.DateTimeFormatter
import kotlin.math.abs

object TimeUtil {

    val DEFAULT_DATE_PATTERN: String = "yyyy/MM/dd"
    val DEFAULT_SEPARATOR: String = "/"
    val DAY_IN_SECONDS: Int = 24 * 3600
    val DAY_IN_MILLS: Int = DAY_IN_SECONDS * 1000

    val ZONE_ID_GMT: ZoneId = ZoneId.of("+00:00")
    val ZONE_ID_BEIJING: ZoneId = ZoneId.of("+08:00")

    fun isValidDate(year: Int, month: Int, day: Int): Boolean {
        if (month < 1 || month > 12) {
            return false
        }
        val lastDay = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            else -> {
                if (isLeapYear(year)) {
                    29
                } else {
                    28
                }
            }
        }
        return day >= 1 && day <= lastDay
    }

    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    fun isValidDateInterval(
        startYear: Int,
        startMonth: Int,
        startDay: Int,
        endYear: Int,
        endMonth: Int,
        endDay: Int
    ): Boolean {
        if (!isValidDate(startYear, startMonth, startDay) || !isValidDate(endYear, endMonth, endDay)) {
            return false
        }
        if (startYear < endYear) {
            return true
        } else if (startYear > endYear) {
            return false
        }

        if (startMonth < endMonth) {
            return true
        } else if (startMonth > endMonth) {
            return false
        }

        return startDay <= endDay
    }


    fun millsToYmd(mills: Long, zoneId: ZoneId?): IntArray {
        val localDate = millsToLocalDate(mills, zoneId)
        return intArrayOf(localDate.year, localDate.monthValue, localDate.dayOfMonth)
    }

    fun millsToYmd(mills: Long, zoneId: String?): IntArray {
        return millsToYmd(mills, ZoneId.of(zoneId))
    }

    fun millsToYmd(mills: Long): IntArray {
        return millsToYmd(mills, ZONE_ID_GMT)
    }

    fun secondsToYmd(seconds: Long, zoneId: ZoneId?): IntArray {
        val localDate = secondsToLocalDate(seconds, zoneId)
        return intArrayOf(localDate.year, localDate.monthValue, localDate.dayOfMonth)
    }

    fun secondsToYmd(seconds: Long, zoneId: String?): IntArray {
        return secondsToYmd(seconds, ZoneId.of(zoneId))
    }

    fun secondsToYmd(seconds: Long): IntArray {
        return secondsToYmd(seconds, ZONE_ID_GMT)
    }

    fun millsToLocalDate(mills: Long, zoneId: ZoneId?): LocalDate {
        val instant = Instant.ofEpochMilli(mills)
        return LocalDate.ofInstant(instant, zoneId)
    }

    fun millsToLocalDate(mills: Long, zoneId: String?): LocalDate {
        return millsToLocalDate(mills, ZoneId.of(zoneId))
    }

    fun millsToLocalDate(mills: Long): LocalDate {
        return millsToLocalDate(mills, ZONE_ID_GMT)
    }

    fun secondsToLocalDate(seconds: Long, zoneId: ZoneId?): LocalDate {
        val instant = Instant.ofEpochSecond(seconds)
        return LocalDate.ofInstant(instant, zoneId)
    }

    fun secondsToLocalDate(seconds: Long, zoneId: String?): LocalDate {
        return secondsToLocalDate(seconds, ZoneId.of(zoneId))
    }

    fun secondsToLocalDate(seconds: Long): LocalDate {
        return secondsToLocalDate(seconds, ZONE_ID_GMT)
    }

    fun localDateTimeToSeconds(localDateTime: LocalDateTime, zoneId: ZoneId?): Long {
        val zonedDateTime = localDateTime.atZone(zoneId)
        return zonedDateTime.toInstant().epochSecond
    }

    fun localDateTimeToSeconds(localDateTime: LocalDateTime, zoneId: String?): Long {
        return localDateTimeToSeconds(localDateTime, ZoneId.of(zoneId))
    }

    fun localDateTimeToSeconds(localDateTime: LocalDateTime): Long {
        return localDateTimeToSeconds(localDateTime, ZONE_ID_GMT)
    }

    fun localDateToSeconds(localDate: LocalDate, zoneId: ZoneId?): Long {
        val localDateTime = localDate.atTime(0, 0, 0, 0)
        return localDateTimeToSeconds(localDateTime, zoneId)
    }

    fun localDateToSeconds(localDate: LocalDate, zoneId: String?): Long {
        return localDateToSeconds(localDate, ZoneId.of(zoneId))
    }

    fun localDateToSeconds(localDate: LocalDate): Long {
        return localDateToSeconds(localDate, ZONE_ID_GMT)
    }

    fun ymdToSeconds(
        year: Int,
        month: Int,
        day: Int,
        zoneId: ZoneId?
    ): Long {
        val localDateTime = LocalDateTime.of(year, month, day, 0, 0, 0, 0)
        return localDateTimeToSeconds(localDateTime, zoneId)
    }

    fun ymdToSeconds(
        year: Int,
        month: Int,
        day: Int,
        zoneId: String?
    ): Long {
        return ymdToSeconds(year, month, day, ZoneId.of(zoneId))
    }

    fun ymdToSeconds(
        year: Int,
        month: Int,
        day: Int
    ): Long {
        return ymdToSeconds(year, month, day, ZONE_ID_GMT)
    }


    //========================================//
    fun localDateTimeToMills(localDateTime: LocalDateTime, zoneId: ZoneId?): Long {
        val zonedDateTime = localDateTime.atZone(zoneId)
        return zonedDateTime.toInstant().toEpochMilli()
    }

    fun localDateTimeToMills(localDateTime: LocalDateTime, zoneId: String?): Long {
        return localDateTimeToMills(localDateTime, ZoneId.of(zoneId))
    }

    fun localDateTimeToMills(localDateTime: LocalDateTime): Long {
        return localDateTimeToMills(localDateTime, ZONE_ID_GMT)
    }

    fun localDateToMills(localDate: LocalDate, zoneId: ZoneId?): Long {
        val localDateTime = localDate.atTime(0, 0, 0, 0)
        return localDateTimeToMills(localDateTime, zoneId)
    }

    fun localDateToMills(localDate: LocalDate, zoneId: String?): Long {
        return localDateToMills(localDate, ZoneId.of(zoneId))
    }

    fun localDateToMills(localDate: LocalDate): Long {
        return localDateToMills(localDate, ZONE_ID_GMT)
    }

    fun ymdToMills(
        year: Int,
        month: Int,
        day: Int,
        zoneId: ZoneId?
    ): Long {
        val localDateTime = LocalDateTime.of(year, month, day, 0, 0, 0, 0)
        return localDateTimeToMills(localDateTime, zoneId)
    }

    fun ymdToMills(
        year: Int,
        month: Int,
        day: Int,
        zoneId: String?
    ): Long {
        return ymdToMills(year, month, day, ZoneId.of(zoneId))
    }

    fun ymdToMills(
        year: Int,
        month: Int,
        day: Int
    ): Long {
        return ymdToMills(year, month, day, ZONE_ID_GMT)
    }


    fun ymdStringToMillsValue(s: String, separator: String = DEFAULT_SEPARATOR, zoneId: ZoneId = ZONE_ID_GMT): Long {
        val ints: IntArray = StringUtil.toInts(s, separator)
        return ymdToMills(ints[0], ints[1], ints[2], zoneId)
    }

    fun ymdStringToMillsValue(s: String, separator: String = DEFAULT_SEPARATOR, zoneId: String): Long {
        return ymdStringToMillsValue(s, separator, ZoneId.of(zoneId))
    }


    fun ymdStringToSecondsValue(s: String, separator: String, zoneId: ZoneId?): Long {
        val ints: IntArray = StringUtil.toInts(s, separator)
        return ymdToSeconds(ints[0], ints[1], ints[2], zoneId)
    }

    fun ymdStringToSecondsValue(s: String, separator: String, zoneId: String?): Long {
        return ymdStringToSecondsValue(s, separator, ZoneId.of(zoneId))
    }

    fun ymdStringToSecondsValue(s: String, zoneId: ZoneId): Long {
        return ymdStringToSecondsValue(s, DEFAULT_SEPARATOR, zoneId)
    }

    fun ymdStringToSecondsValue(s: String, separator: String): Long {
        return ymdStringToSecondsValue(s, separator, ZONE_ID_GMT)
    }

    fun ymdStringToSecondsValue(s: String): Long {
        return ymdStringToSecondsValue(s, DEFAULT_SEPARATOR, ZONE_ID_GMT)
    }

    fun ymdStringToMills(s: String, sep: String = DEFAULT_SEPARATOR, zoneId: ZoneId = ZONE_ID_GMT): Long? {
        val integers: Array<Int?>? = StringUtil.toIntegers(s, sep)
        if (integers == null || integers.size < 3) {
            return null
        }
        for (i in integers) {
            if (i == null) {
                return null
            }
        }

        return ymdToMills(integers[0]!!, integers[1]!!, integers[2]!!, zoneId)
    }

    fun ymdStringToMills(s: String, sep: String = DEFAULT_SEPARATOR, zoneId: String?): Long? {
        return ymdStringToMills(s, sep, ZoneId.of(zoneId))
    }

    fun ymdStringToSeconds(s: String, sep: String = DEFAULT_SEPARATOR, zoneId: ZoneId = ZONE_ID_GMT): Long? {
        val integers: Array<Int?>? = StringUtil.toIntegers(s, sep)
        if (integers == null || integers.size < 3) {
            return null
        }
        for (i in integers) {
            if (i == null) {
                return null
            }
        }

        return ymdToSeconds(integers[0]!!, integers[1]!!, integers[2]!!, zoneId)
    }

    fun ymdStringToSeconds(s: String, sep: String, zoneId: String?): Long? {
        return ymdStringToSeconds(s, sep, ZoneId.of(zoneId))
    }

    /**
     * 数字转为时间戳
     *
     * @param ymdNumber 日期数字，如 20200101表示2020年1月1日
     * @param zoneId    时区
     * @return 时间戳
     */
    fun ymdNumberToSeconds(ymdNumber: Int, zoneId: ZoneId?): Long {
        var ymdNumber = ymdNumber

        val d = ymdNumber % 100
        ymdNumber /= 100
        val m = ymdNumber % 100
        val y = ymdNumber / 100

        return ymdToSeconds(y, m, d, zoneId)
    }

    fun ymdNumberToSeconds(ymdNumber: Int): Long {
        return ymdNumberToSeconds(ymdNumber, ZONE_ID_GMT)
    }

    fun millsToYmdString(mills: Long, separator: String = DEFAULT_SEPARATOR, zoneId: ZoneId = ZONE_ID_GMT): String {
        val ints = millsToYmd(mills, zoneId)
        return ints[0].toString() + separator + ints[1] + separator + ints[2]
    }

    fun millsToYmdString(mills: Long, separator: String, zoneId: String?): String {
        return millsToYmdString(mills, separator, ZoneId.of(zoneId))
    }

    fun secondsToYmdString(seconds: Long, separator: String = DEFAULT_SEPARATOR, zoneId: ZoneId = ZONE_ID_GMT): String {
        return millsToYmdString(seconds * 1000, separator, zoneId)
    }

    fun secondsToYmdString(seconds: Long, separator: String, zoneId: String): String {
        return secondsToYmdString(seconds, separator, ZoneId.of(zoneId))
    }

    fun millsValueToDateString(mills: Long, formatter: DateTimeFormatter?, zoneId: ZoneId?): String {
        val instant = Instant.ofEpochMilli(mills)
        val localDateTime = LocalDateTime.ofInstant(instant, zoneId)
        return localDateTime.format(formatter)
    }

    fun millsValueToDateString(mills: Long, formatter: DateTimeFormatter?, zoneId: String?): String {
        return millsValueToDateString(mills, formatter, ZoneId.of(zoneId))
    }

    fun millsValueToDateString(mills: Long, format: String?, zoneId: ZoneId?): String {
        return millsValueToDateString(mills, DateTimeFormatter.ofPattern(format), zoneId)
    }

    fun millsValueToDateString(mills: Long, format: String?, zoneId: String?): String {
        return millsValueToDateString(mills, DateTimeFormatter.ofPattern(format), ZoneId.of(zoneId))
    }

    fun millsValueToDateString(mills: Long, zoneId: ZoneId?): String {
        return millsValueToDateString(mills, DateTimeFormatter.BASIC_ISO_DATE, zoneId)
    }

    fun millsValueToDateString(mills: Long, format: String?): String {
        return millsValueToDateString(mills, DateTimeFormatter.ofPattern(format), ZONE_ID_GMT)
    }

    fun millsValueToDateString(mills: Long): String {
        return millsValueToDateString(mills, DateTimeFormatter.BASIC_ISO_DATE, ZONE_ID_GMT)
    }

    fun millsToDateString(mills: Long?, formatter: DateTimeFormatter?, zoneId: ZoneId?): String? {
        if (mills == null) {
            return null
        }

        return millsValueToDateString(mills, formatter, zoneId)
    }

    fun millsToDateString(mills: Long?, formatter: DateTimeFormatter?, zoneId: String?): String? {
        return millsToDateString(mills, formatter, ZoneId.of(zoneId))
    }

    fun millsToDateString(mills: Long?, format: String?, zoneId: ZoneId?): String? {
        return millsToDateString(mills, DateTimeFormatter.ofPattern(format), zoneId)
    }

    fun millsToDateString(mills: Long?, format: String?, zoneId: String?): String? {
        return millsToDateString(mills, DateTimeFormatter.ofPattern(format), ZoneId.of(zoneId))
    }

    fun millsToDateString(mills: Long?, zoneId: ZoneId?): String? {
        return millsToDateString(mills, DateTimeFormatter.BASIC_ISO_DATE, zoneId)
    }

    fun millsToDateString(mills: Long?, format: String?): String? {
        return millsToDateString(mills, DateTimeFormatter.ofPattern(format), ZONE_ID_GMT)
    }

    fun millsToDateString(mills: Long?): String? {
        return millsToDateString(mills, DateTimeFormatter.BASIC_ISO_DATE, ZONE_ID_GMT)
    }

    fun secondsValueToDateString(seconds: Long, formatter: DateTimeFormatter?, zoneId: ZoneId?): String {
        val instant = Instant.ofEpochSecond(seconds)
        val localDateTime = LocalDateTime.ofInstant(instant, zoneId)
        return localDateTime.format(formatter)
    }

    fun secondsValueToDateString(seconds: Long, formatter: DateTimeFormatter?, zoneId: String?): String {
        return secondsValueToDateString(seconds, formatter, ZoneId.of(zoneId))
    }

    fun secondsValueToDateString(seconds: Long, format: String?, zoneId: ZoneId?): String {
        return secondsValueToDateString(seconds, DateTimeFormatter.ofPattern(format), zoneId)
    }

    fun secondsValueToDateString(seconds: Long, format: String?, zoneId: String?): String {
        return secondsValueToDateString(seconds, DateTimeFormatter.ofPattern(format), ZoneId.of(zoneId))
    }

    fun secondsValueToDateString(seconds: Long, zoneId: ZoneId?): String {
        return secondsValueToDateString(seconds, DateTimeFormatter.BASIC_ISO_DATE, zoneId)
    }

    fun secondsValueToDateString(seconds: Long, format: String?): String {
        return secondsValueToDateString(seconds, DateTimeFormatter.ofPattern(format), ZONE_ID_GMT)
    }

    fun secondsValueToDateString(seconds: Long): String {
        return secondsValueToDateString(seconds, DateTimeFormatter.BASIC_ISO_DATE, ZONE_ID_GMT)
    }

    fun secondsToDateString(seconds: Long?, formatter: DateTimeFormatter?, zoneId: ZoneId?): String? {
        if (seconds == null) {
            return null
        }
        return secondsValueToDateString(seconds, formatter, zoneId)
    }

    fun secondsToDateString(seconds: Long?, formatter: DateTimeFormatter?, zoneId: String?): String? {
        return secondsToDateString(seconds, formatter, ZoneId.of(zoneId))
    }

    fun secondsToDateString(seconds: Long?, format: String?, zoneId: ZoneId?): String? {
        return secondsToDateString(seconds, DateTimeFormatter.ofPattern(format), zoneId)
    }

    fun secondsToDateString(seconds: Long?, format: String?, zoneId: String?): String? {
        return secondsToDateString(seconds, DateTimeFormatter.ofPattern(format), ZoneId.of(zoneId))
    }

    fun secondsToDateString(seconds: Long?, zoneId: ZoneId?): String? {
        return secondsToDateString(seconds, DateTimeFormatter.BASIC_ISO_DATE, zoneId)
    }

    fun secondsToDateString(seconds: Long?, format: String?): String? {
        return secondsToDateString(seconds, DateTimeFormatter.ofPattern(format), ZONE_ID_GMT)
    }

    fun secondsToDateString(seconds: Long?): String? {
        return secondsToDateString(seconds, DateTimeFormatter.BASIC_ISO_DATE, ZONE_ID_GMT)
    }

    fun getTodayMills(zoneId: ZoneId?): Long {
        val localDate = LocalDate.ofInstant(Instant.now(), zoneId)
        val localDateTime = localDate.atTime(0, 0, 0, 0)
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli()
    }

    fun getTodayMills(zoneId: String?): Long {
        return getTodayMills(ZoneId.of(zoneId))
    }

    fun getTodayMills(): Long {
        return getTodayMills(ZONE_ID_GMT)
    }

    fun getTodaySeconds(zoneId: ZoneId?): Long {
        val localDate = LocalDate.ofInstant(Instant.now(), zoneId)
        val localDateTime = localDate.atTime(0, 0, 0, 0)
        return localDateTime.atZone(zoneId).toInstant().epochSecond
    }

    fun getTodaySeconds(zoneId: String?): Long {
        return getTodaySeconds(ZoneId.of(zoneId))
    }

    fun getTodaySeconds(): Long {
        return getTodaySeconds(ZONE_ID_GMT)
    }

    fun todayToDateString(formatter: DateTimeFormatter?, zoneId: ZoneId?): String {
        return LocalDate.ofInstant(Instant.now(), zoneId).format(formatter)
    }

    fun todayToDateString(formatter: DateTimeFormatter?, zoneId: String?): String {
        return todayToDateString(formatter, ZoneId.of(zoneId))
    }

    fun todayToDateString(format: String?, zoneId: ZoneId?): String {
        return LocalDate.ofInstant(Instant.now(), zoneId).format(DateTimeFormatter.ofPattern(format))
    }

    fun todayToDateString(format: String?, zoneId: String?): String {
        return todayToDateString(DateTimeFormatter.ofPattern(format), ZoneId.of(zoneId))
    }

    fun todayToDateString(formatter: DateTimeFormatter?): String {
        return todayToDateString(formatter, ZONE_ID_GMT)
    }

    fun todayToDateString(): String {
        return todayToDateString(DateTimeFormatter.BASIC_ISO_DATE, ZONE_ID_GMT)
    }


    // =====================================================//
    fun millsToLocalTime(mills: Long, zoneId: ZoneId?): LocalTime {
        val instant = Instant.ofEpochMilli(mills)
        return LocalTime.ofInstant(instant, zoneId)
    }

    fun millsToLocalTime(mills: Long, zoneId: String?): LocalTime {
        return millsToLocalTime(mills, ZoneId.of(zoneId))
    }

    fun millsToLocalTime(mills: Long): LocalTime {
        return millsToLocalTime(mills, ZONE_ID_GMT)
    }

    fun secondsToLocalTime(seconds: Long, zoneId: ZoneId?): LocalTime {
        val instant = Instant.ofEpochSecond(seconds)
        return LocalTime.ofInstant(instant, zoneId)
    }

    fun secondsToLocalTime(seconds: Long, zoneId: String?): LocalTime {
        return secondsToLocalTime(seconds, ZoneId.of(zoneId))
    }

    fun secondsToLocalTime(seconds: Long): LocalTime {
        return secondsToLocalTime(seconds, ZONE_ID_GMT)
    }


    // ============================================================//
    fun secondsToZonedDateTime(seconds: Long, zoneId: ZoneId?): ZonedDateTime {
        val instant = Instant.ofEpochSecond(seconds)
        return ZonedDateTime.ofInstant(instant, zoneId)
    }

    fun secondsToZonedDateTime(seconds: Long, zoneId: String?): ZonedDateTime {
        return secondsToZonedDateTime(seconds, ZoneId.of(zoneId))
    }

    fun secondsToZonedDateTime(seconds: Long): ZonedDateTime {
        return secondsToZonedDateTime(seconds, ZONE_ID_GMT)
    }

    fun millsToZonedDateTime(mills: Long, zoneId: ZoneId?): ZonedDateTime {
        val instant = Instant.ofEpochMilli(mills)
        return ZonedDateTime.ofInstant(instant, zoneId)
    }

    fun millsToZonedDateTime(mills: Long, zoneId: String?): ZonedDateTime {
        return millsToZonedDateTime(mills, ZoneId.of(zoneId))
    }

    fun millsToZonedDateTime(mills: Long): ZonedDateTime {
        return millsToZonedDateTime(mills, ZONE_ID_GMT)
    }


    // ==============================================================//
    fun millsToHMS(mills: Long, zoneId: ZoneId?): IntArray {
        val localTime = millsToLocalTime(mills, zoneId)
        return intArrayOf(localTime.hour, localTime.minute, localTime.second)
    }

    fun millsToHMS(mills: Long, zoneId: String?): IntArray {
        return millsToHMS(mills, ZoneId.of(zoneId))
    }

    fun millsToHMS(mills: Long): IntArray {
        return millsToHMS(mills, ZONE_ID_GMT)
    }

    fun secondsToHMS(seconds: Long, zoneId: ZoneId?): IntArray {
        val localTime = secondsToLocalTime(seconds, zoneId)
        return intArrayOf(localTime.hour, localTime.minute, localTime.second)
    }

    fun secondsToHMS(seconds: Long, zoneId: String?): IntArray {
        return secondsToHMS(seconds, ZoneId.of(zoneId))
    }

    fun secondsToHMS(seconds: Long): IntArray {
        return secondsToHMS(seconds, ZONE_ID_GMT)
    }

    fun nowMills(): Long {
        return Instant.now().toEpochMilli()
    }

    fun nowSecond(): Long {
        return Instant.now().epochSecond
    }

    fun nowToString(): String {
        return nowToString("yyyy-MM-dd hh:mm:ss", ZoneId.of("+08:00"))
    }

    fun nowToString(pattern: String?): String {
        return nowToString(pattern, ZoneId.of("+08:00"))
    }

    fun nowToString(pattern: String?, zoneId: String?): String {
        return nowToString(pattern, ZoneId.of(zoneId))
    }

    fun nowToString(pattern: String?, zoneId: ZoneId?): String {
        return LocalDateTime.ofInstant(Instant.now(), zoneId).format(DateTimeFormatter.ofPattern(pattern))
    }

    fun localDateTimeToString(localDateTime: LocalDateTime, pattern: String?): String {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern))
    }

    fun startMillsOfSameDay(mills: Long, zoneId: ZoneId?): Long {
        val instant = Instant.ofEpochMilli(mills)
        val localDateTime = LocalDate.ofInstant(instant, zoneId).atTime(0, 0, 0, 0)
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli()
    }

    fun startMillsOfSameDay(mills: Long, zoneId: String?): Long {
        return startMillsOfSameDay(mills, ZoneId.of(zoneId))
    }

    fun startMillsOfSameDay(mills: Long): Long {
        return startMillsOfSameDay(mills, ZONE_ID_GMT)
    }

    fun endMillsOfSameDay(mills: Long, zoneId: ZoneId?): Long {
        val instant = Instant.ofEpochMilli(mills)
        val localDateTime = LocalDate.ofInstant(instant, zoneId).atTime(23, 59, 59, 999999999)
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli()
    }

    fun endMillsOfSameDay(mills: Long, zoneId: String?): Long {
        return endMillsOfSameDay(mills, ZoneId.of(zoneId))
    }

    fun endMillsOfSameDay(mills: Long): Long {
        return endMillsOfSameDay(mills, ZONE_ID_GMT)
    }

    fun startSecondsOfSameDay(seconds: Long, zoneId: ZoneId?): Long {
        val instant = Instant.ofEpochSecond(seconds)
        val localDateTime = LocalDate.ofInstant(instant, zoneId).atTime(0, 0, 0, 0)
        return localDateTime.atZone(zoneId).toInstant().epochSecond
    }

    fun startSecondsOfSameDay(seconds: Long, zoneId: String?): Long {
        return startSecondsOfSameDay(seconds, ZoneId.of(zoneId))
    }

    fun startSecondsOfSameDay(seconds: Long): Long {
        return startSecondsOfSameDay(seconds, ZONE_ID_GMT)
    }

    fun endSecondsOfSameDay(seconds: Long, zoneId: ZoneId?): Long {
        val instant = Instant.ofEpochSecond(seconds)
        val localDateTime = LocalDate.ofInstant(instant, zoneId).atTime(23, 59, 59, 999999999)
        return localDateTime.atZone(zoneId).toInstant().epochSecond
    }

    fun endSecondsOfSameDay(seconds: Long, zoneId: String?): Long {
        return endSecondsOfSameDay(seconds, ZoneId.of(zoneId))
    }

    fun endSecondsOfSameDay(seconds: Long): Long {
        return endSecondsOfSameDay(seconds, ZONE_ID_GMT)
    }


    fun isSameDayOfMills(mills1: Long, mills2: Long, zoneId: ZoneId?): Boolean {
        if (abs((mills1 - mills2).toDouble()) > DAY_IN_MILLS) {
            return false
        }

        val localDate1 = millsToLocalDate(mills1, zoneId)
        val localDate2 = millsToLocalDate(mills2, zoneId)

        return localDate1.isEqual(localDate2)
    }

    fun isSameDayOfMills(mills1: Long, mills2: Long, zoneId: String?): Boolean {
        return isSameDayOfMills(mills1, mills2, ZoneId.of(zoneId))
    }

    fun isSameDayOfMills(mills1: Long, mills2: Long): Boolean {
        return isSameDayOfMills(mills1, mills2, ZONE_ID_GMT)
    }

    fun isSameDayOfSeconds(seconds1: Long, seconds2: Long, zoneId: ZoneId?): Boolean {
        if (abs((seconds1 - seconds2).toDouble()) > DAY_IN_SECONDS) {
            return false
        }

        val localDate1 = secondsToLocalDate(seconds1, zoneId)
        val localDate2 = secondsToLocalDate(seconds2, zoneId)

        return localDate1.isEqual(localDate2)
    }

    fun isSameDayOfSeconds(seconds1: Long, seconds2: Long, zoneId: String?): Boolean {
        return isSameDayOfSeconds(seconds1, seconds2, ZoneId.of(zoneId))
    }

    fun isSameDayOfSeconds(seconds1: Long, seconds2: Long): Boolean {
        return isSameDayOfSeconds(seconds1, seconds2, ZONE_ID_GMT)
    }


    // ====================================================================//
    fun startMillsOfYear(year: Int, zoneId: ZoneId?): Long {
        val localDateTime = LocalDateTime.of(year, 1, 1, 0, 0, 0, 0)
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli()
    }

    fun startMillsOfYear(year: Int, zoneId: String?): Long {
        return startMillsOfYear(year, ZoneId.of(zoneId))
    }

    fun startMillsOfYear(year: Int): Long {
        return startMillsOfYear(year, ZONE_ID_GMT)
    }

    fun endMillsOfYear(year: Int, zoneId: ZoneId?): Long {
        val localDateTime = LocalDateTime.of(year, 12, 31, 23, 59, 59, 999999999)
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli()
    }

    fun endMillsOfYear(year: Int, zoneId: String?): Long {
        return endMillsOfYear(year, ZoneId.of(zoneId))
    }

    fun endMillsOfYear(year: Int): Long {
        return endMillsOfYear(year, ZONE_ID_GMT)
    }

    fun startSecondsOfYear(year: Int, zoneId: ZoneId?): Long {
        val localDateTime = LocalDateTime.of(year, 1, 1, 0, 0, 0, 0)
        return localDateTime.atZone(zoneId).toInstant().epochSecond
    }

    fun startSecondsOfYear(year: Int, zoneId: String?): Long {
        return startSecondsOfYear(year, ZoneId.of(zoneId))
    }

    fun startSecondsOfYear(year: Int): Long {
        return startSecondsOfYear(year, ZONE_ID_GMT)
    }

    fun endSecondsOfYear(year: Int, zoneId: ZoneId?): Long {
        val localDateTime = LocalDateTime.of(year, 12, 31, 23, 59, 59, 999999999)
        return localDateTime.atZone(zoneId).toInstant().epochSecond
    }

    fun endSecondsOfYear(year: Int, zoneId: String?): Long {
        return endSecondsOfYear(year, ZoneId.of(zoneId))
    }

    fun endSecondsOfYear(year: Int): Long {
        return endSecondsOfYear(year, ZONE_ID_GMT)
    }
}