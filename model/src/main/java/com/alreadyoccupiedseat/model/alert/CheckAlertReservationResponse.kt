package com.alreadyoccupiedseat.model.alert

data class CheckAlertReservationResponse(
    val alertReservationAvailability: AlertReservationAvailability,
    val alertReservationStatus: AlertReservationStatus
)