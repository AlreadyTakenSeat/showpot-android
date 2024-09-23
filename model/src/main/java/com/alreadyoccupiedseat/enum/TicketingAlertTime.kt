package com.alreadyoccupiedseat.enum

import androidx.annotation.Keep

@Keep
enum class TicketingAlertTime(val koreanText: String) {
    BEFORE_24("24시간 전"),
    BEFORE_6("6시간 전"),
    BEFORE_1("1시간 전"),
}