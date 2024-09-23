package com.alreadyoccupiedseat.model.temp

import androidx.annotation.Keep

@Keep
data class AlertReservedShow(
    val cursorId: String,
    val cursorValue: String,
    val endAt: String,
    val id: String,
    val imageURL: String,
    val location: String,
    val startAt: String,
    val title: String
)