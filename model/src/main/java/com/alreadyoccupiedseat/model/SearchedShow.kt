package com.alreadyoccupiedseat.model

import androidx.annotation.Keep

@Keep
data class SearchedShow(
    val endAt: String,
    val id: String,
    val imageURL: String,
    val location: String,
    val startAt: String,
    val title: String
)