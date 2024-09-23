package com.alreadyoccupiedseat.model.alert

import androidx.annotation.Keep

@Keep
data class AlertReservationStatus(
    val before1: Boolean,
    val before24: Boolean,
    val before6: Boolean
)