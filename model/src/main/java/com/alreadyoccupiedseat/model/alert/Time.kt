package com.alreadyoccupiedseat.model.alert

import androidx.annotation.Keep


@Keep
data class Time(
    val beforeMinutes: Int,
    val canReserve: Boolean,
    val isReserved: Boolean
)