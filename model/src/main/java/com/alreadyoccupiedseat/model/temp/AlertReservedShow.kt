package com.alreadyoccupiedseat.model.temp

import androidx.annotation.Keep

@Keep
data class AlertReservedShow(
    val cursorId: String,
    val id: String,
    val imageURL: String,
    val location: String,
    val title: String,
    val ticketingAt: String,
    val startAt: String,
    val endAt: String
)