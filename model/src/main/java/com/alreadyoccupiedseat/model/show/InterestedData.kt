package com.alreadyoccupiedseat.model.show

data class InterestedData (
    val id: String,
    val title: String,
    val startAt: String,
    val endAt: String,
    val interestShowId: String,
    val interestedAt: String,
    val location: String,
    val posterImageURL: String
)