package com.alreadyoccupiedseat.model

data class Data(
    val artists: List<Artist>,
    val genres: List<Genre>,
    val hasTicketingOpenSchedule: Boolean,
    val id: String,
    val location: String,
    val posterImageURL: String,
    val reservationAt: String,
    val showTicketingTimes: List<ShowTicketingTime>,
    val title: String,
    val viewCount: Int
)