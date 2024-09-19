package com.alreadyoccupiedseat.model.show

import androidx.annotation.Keep

@Keep
data class Data(
    val id: String,
    val isOpen: Boolean,
    val location: String,
    val posterImageURL: String,
    val ticketingAt: String,
    val title: String
)