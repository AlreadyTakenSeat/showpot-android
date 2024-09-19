package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class TicketingAndShowInfo(
    val showDateTime: String,
    val ticketingDateTime: String,
    val ticketingURL: String
)