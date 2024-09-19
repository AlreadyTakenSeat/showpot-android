package com.alreadyoccupiedseat.model.alert

import androidx.annotation.Keep

@Keep
data class TicketingAlertRequest(
    val alertTimes: List<String>
)