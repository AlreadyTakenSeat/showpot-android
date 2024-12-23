package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class TicketingBoxSelectionState(
    val isAvailable: Boolean = false,
    val isSelected: Boolean = false,
    val minute: Int = 0,
)
