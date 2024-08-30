package com.alreadyoccupiedseat.model.alert

data class AlertReservationAvailability(
    val canReserve1: Boolean,
    val canReserve24: Boolean,
    val canReserve6: Boolean
)