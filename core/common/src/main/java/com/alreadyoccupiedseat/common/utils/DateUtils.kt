package com.alreadyoccupiedseat.common.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private val alertTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm")

fun getCurrentDateTime(): String {
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.format(alertTimeFormatter)
}

fun subtractMinutesFromDateTime(dateTime: String, minutes: Long): String {
    val parsedDateTime = LocalDateTime.parse(dateTime, alertTimeFormatter)
    val updatedDateTime = parsedDateTime.minusMinutes(minutes)
    return updatedDateTime.format(alertTimeFormatter)
}

fun convertToKoreanTimeFormat(dateTimeStr: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm")
    val dateTime = LocalDateTime.parse(dateTimeStr, inputFormatter)

    val hour = dateTime.hour
    val minute = dateTime.minute

    val period = if (hour < 12) "오전" else "오후"
    val hour12 = if (hour % 12 == 0) 12 else hour % 12
    val formattedMinute = String.format("%02d", minute)

    return "$period ${hour12}:${formattedMinute}"
}

/**
 * @return date1이 date2보다 크거나 같으면 true, 아니면 false
 */
fun isDate1GreaterOrEqual(date1: String, date2: String): Boolean {
    val dateTime1 = LocalDateTime.parse(date1, alertTimeFormatter)
    val dateTime2 = LocalDateTime.parse(date2, alertTimeFormatter)
    return dateTime1 >= dateTime2
}