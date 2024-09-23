package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class ShowTicketingTime(
    val ticketingAt: String,
    val ticketingType: String
)