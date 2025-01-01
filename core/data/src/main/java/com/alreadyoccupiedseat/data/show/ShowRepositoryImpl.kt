package com.alreadyoccupiedseat.data.show

import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.alert.Times
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
        cursorId: String?,
    ): Result<PagingData<ShowPreview>> {
        return showDataSource.getEntireShow(
            sort = sort,
            onlyOpenSchedule = onlyOpenSchedule,
            size = size,
            cursorId = cursorId,
        )
    }

    /** 관심 공연 목록 조회 ***/
    override suspend fun getInterestedShowList(size: Int, cursorId: String?): Result<PagingData<InterestedData>> {
        return showDataSource.getInterestedShowList(size, cursorId)
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

    override suspend fun registerShowInterest(showId: String): Result<Boolean> {
        return showDataSource.registerShowInterest(showId)
    }
    override suspend fun registerShowUnInterest(showId: String): Result<Boolean> {
        return showDataSource.registerShowUnInterest(showId)
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
    ): Times {
        return showDataSource.checkAlertReservation(showId, ticketingApiType)
    }

    override suspend fun getAlertReservedShow(
        cursorId: String?,
        type: String,
        size: Int,
    ): Result<PagingData<AlertReservedShow>> {
        return showDataSource.getAlertReservedShow(cursorId = cursorId, type = type, size = size)
    }

}