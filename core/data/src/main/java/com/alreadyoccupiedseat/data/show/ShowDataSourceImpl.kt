package com.alreadyoccupiedseat.data.show

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.alert.CheckAlertReservationResponse
import com.alreadyoccupiedseat.model.alert.TicketingAlertRequest
import com.alreadyoccupiedseat.model.show.ShowPreview
import com.alreadyoccupiedseat.model.show.InterestedData
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.model.temp.AlertReservedShow
import com.alreadyoccupiedseat.network.ShowService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ShowDataSourceImpl @Inject constructor(
    private val showService: ShowService,
    @ApplicationContext private val context: Context
) : ShowDataSource {

    override suspend fun getEntireShow(
        sort: String,
        onlyOpenSchedule: Boolean,
        size: Int,
    ): List<ShowPreview> {
        return showService.getEntireShow(
            sort = sort,
            onlyOpenSchedule = onlyOpenSchedule,
            size = size
        ).body()?.data?.data ?: emptyList()
    }

    override suspend fun searchShows(
        cursorId: String?,
        size: Int,
        search: String
    ): List<SearchedShow> {
        return showService.searchShows(
            cursorId = cursorId,
            size = size,
            search = search,
        ).body()?.data?.data ?: emptyList()
    }

    /** 관심 공연 목록 조회 ***/
    override suspend fun getInterestedShowList(size: Int): List<InterestedData> {
        return showService.getInterestedShowList(size).body()?.data?.data ?: emptyList()
    }

    @SuppressLint("HardwareIds")
    override suspend fun getShowDetail(showId: String): ShowDetail {
        val viewIdentifier =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return showService.getShowDetail(showId, viewIdentifier).body()?.data
            ?: throw Exception("Show not found")
    }

    /** 관심 공연 등록, 취소 ***/
    override suspend fun registerShowInterest(showId: String): Boolean {
        return showService.registerShowInterest(showId).body()?.data?.hasInterest ?: false
    }

    override suspend fun registerTicketingAlert(
        showId: String,
        ticketingApiType: String,
        alertTimes: List<String>
    ): Result<Unit> {
        return runCatching {
            showService.registerTicketingAlert(
                showId,
                ticketingApiType,
                TicketingAlertRequest(alertTimes)
            )
        }
    }

    override suspend fun checkAlertReservation(
        showId: String,
        ticketingApiType: String
    ): CheckAlertReservationResponse {
        return showService.checkAlertReservation(showId, ticketingApiType).body()?.data
            ?: throw Exception("Check Alert Reservation failed")
    }

    /** 알림 설정한 공연 목록 조회 ***/
    override suspend fun getAlertReservedShow(size: Int, type: String): List<AlertReservedShow> {
        return showService.getAlertReservedShow(size, type).body()?.data?.data ?: emptyList()
    }

}