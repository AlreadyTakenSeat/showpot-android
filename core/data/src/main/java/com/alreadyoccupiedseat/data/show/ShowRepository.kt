package com.alreadyoccupiedseat.data.show

import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.alert.Times
import com.alreadyoccupiedseat.model.show.ShowPreview
import com.alreadyoccupiedseat.model.show.InterestedData
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.model.temp.AlertReservedShow

interface ShowRepository {
    suspend fun getEntireShow(
        sort: String,
        onlyOpenSchedule: Boolean,
        size: Int,
        cursorId: String? = null,
    ): Result<PagingData<ShowPreview>>

    /** 관심 공연 목록 조회***/
    suspend fun getInterestedShowList(
        size: Int,
        cursorId: String? = null,
    ): Result<PagingData<InterestedData>>

    suspend fun searchShows(
        cursorId: String? = null,
        size: Int,
        search: String
    ): List<SearchedShow>
    suspend fun getShowDetail(showId: String): Result<ShowDetail>

    suspend fun registerShowInterest(showId: String): Result<Boolean>

    suspend fun registerShowUnInterest(showId: String): Result<Boolean>

    suspend fun registerTicketingAlert(
        showId: String,
        ticketingApiType: String,
        alertTimes: List<String>,
    ): Result<Unit>

    suspend fun checkAlertReservation(
        showId: String,
        ticketingApiType: String,
    ): Times

    suspend fun getAlertReservedShow(cursorId: String?, type: String, size: Int): Result<PagingData<AlertReservedShow>>

}