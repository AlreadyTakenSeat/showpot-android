package com.alreadyoccupiedseat.model.show

import androidx.annotation.Keep

@Keep
data class Seat(
    val price: Int,
    val seatType: String
)