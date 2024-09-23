package com.alreadyoccupiedseat.model.alert

import androidx.annotation.Keep

@Keep
data class CheckAlertReservationResponse(
    val alertReservationAvailability: AlertReservationAvailability,
    val alertReservationStatus: AlertReservationStatus
)