package com.alreadyoccupiedseat.model.show

import androidx.annotation.Keep

@Keep
data class TicketingTime(
    val ticketingApiType: String,
    val ticketingAt: String
)