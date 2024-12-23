package com.alreadyoccupiedseat.enum

import androidx.annotation.Keep

@Keep
enum class TicketingAlertTime(val minute: Int, val beforeText: String) {
    BEFORE_5(5, "5분 전"),
    BEFORE_10(10, "10분 전"),
    BEFORE_30(30, "30분 전"),
    BEFORE_60(60,  "1시간 전"),
}