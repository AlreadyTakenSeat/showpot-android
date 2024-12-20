package com.alreadyoccupiedseat.data.show

import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.alert.CheckAlertReservationResponse
import com.alreadyoccupiedseat.model.show.ShowPreview
import com.alreadyoccupiedseat.model.show.InterestedData
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.model.temp.AlertReservedShow
import javax.inject.Inject


class ShowRepositoryImpl @Inject constructor(
    private val showDataSource: ShowDataSource,
) : ShowRepository {

    override suspend fun getEntireShow(
        sort: String,
        onlyOpenSchedule: Boolean,
        size: Int,
    ): List<ShowPreview> {
        return showDataSource.getEntireShow(
            sort = sort,
            onlyOpenSchedule = onlyOpenSchedule,
            size = size
        )
    }

    /** 관심 공연 목록 조회 ***/
    override suspend fun getInterestedShowList(size: Int): List<InterestedData> {
        return showDataSource.getInterestedShowList(size)
    }

    override suspend fun searchShows(
        cursorId: String?,
        size: Int,
        search: String,
    ): List<SearchedShow> {
        return showDataSource.searchShows(
            cursorId = cursorId,
            size = size,
            search = search,
        )
    }


    override suspend fun getShowDetail(showId: String): ShowDetail {
        return showDataSource.getShowDetail(showId)
    }

    override suspend fun registerShowInterest(showId: String): Boolean {
        return showDataSource.registerShowInterest(showId)
    }

    override suspend fun registerTicketingAlert(
        showId: String,
        ticketingApiType: String,
        alertTimes: List<String>
    ): Result<Unit> {
        return showDataSource.registerTicketingAlert(showId, ticketingApiType, alertTimes)
    }

    override suspend fun checkAlertReservation(
        showId: String,
        ticketingApiType: String,
    ): CheckAlertReservationResponse {
        return showDataSource.checkAlertReservation(showId, ticketingApiType)
    }

    /** 알림 설정한 공연 목록 조회 ***/
    override suspend fun getAlertReservedShow(size: Int, type: String): List<AlertReservedShow> {
        return showDataSource.getAlertReservedShow(size, type)
    }

}