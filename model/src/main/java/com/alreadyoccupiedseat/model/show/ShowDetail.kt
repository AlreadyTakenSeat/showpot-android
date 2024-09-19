package com.alreadyoccupiedseat.model.show

import androidx.annotation.Keep
import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.Genre
@Keep
data class ShowDetail(
    val artists: List<Artist>,
    val endDate: String,
    val genres: List<Genre>,
    val id: String,
    val location: String,
    val name: String,
    val posterImageURL: String,
    val isInterested: Boolean,
    val seats: List<Seat>,
    val startDate: String,
    val ticketingSites: List<TicketingSite>,
    val ticketingTimes: List<TicketingTime>
)