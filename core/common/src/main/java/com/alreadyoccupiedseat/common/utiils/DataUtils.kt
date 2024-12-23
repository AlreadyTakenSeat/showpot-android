package com.alreadyoccupiedseat.common.utiils

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