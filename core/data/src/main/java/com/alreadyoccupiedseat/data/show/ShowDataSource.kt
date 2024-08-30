package com.alreadyoccupiedseat.data.show

import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.alert.CheckAlertReservationResponse
import com.alreadyoccupiedseat.model.show.Data
import com.alreadyoccupiedseat.model.show.InterestedData
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.model.temp.AlarmReservedShow

interface ShowDataSource {

    suspend fun getEntireShow(
        sort: String,
        onlyOpenSchedule: Boolean,
        size: Int
    ): List<Data>

    suspend fun getInterestedShowList(
        size: Int
    ): List<InterestedData>

    suspend fun searchShows(
        cursorId: String? = null,
        size: Int,
        search: String
    ): List<SearchedShow>

    suspend fun getShowDetail(showId: String): ShowDetail

    suspend fun registerShowInterest(showId: String): Boolean

    suspend fun registerTicketingAlert(
        showId: String,
        ticketingApiType: String,
        alertTimes: List<String>,
    ): Result<Unit>

    suspend fun checkAlertReservation(
        showId: String,
        ticketingApiType: String,
    ): CheckAlertReservationResponse

    /** 알림 설정한 공연 목록 조회 ***/
    suspend fun getAlarmReservedShow(size: Int, type: String): List<AlarmReservedShow>

}