package com.alreadyoccupiedseat.model.alert

import androidx.annotation.Keep

@Keep
data class AlertReservationAvailability(
    val canReserve1: Boolean,
    val canReserve24: Boolean,
    val canReserve6: Boolean
)