package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class Alert(
    val title: String,
    val message: String,
    val notifiedAt: String,
    val showId: String,
    val showImageURL: String
)

@Keep
data class AlertExist(
    val isExist: Boolean
)
