package com.alreadyoccupiedseat.data.show

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.alert.TicketingAlertRequest
import com.alreadyoccupiedseat.model.show.Data
import com.alreadyoccupiedseat.model.show.InterestedData
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.model.temp.AlarmReservedShow
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
    ): List<Data> {
        return showService.getEntireShow(
            sort = sort,
            onlyOpenSchedule = onlyOpenSchedule,
            size = size
        ).body()?.data ?: emptyList()
    }

    override suspend fun getInterestedShowList(size: Int): List<InterestedData> {
        return showService.getInterestedShowList(size).body()?.data ?: emptyList()
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
        ).body()?.data ?: emptyList()
    }

    @SuppressLint("HardwareIds")
    override suspend fun getShowDetail(showId: String): ShowDetail {
        val viewIdentifier =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return showService.getShowDetail(showId, viewIdentifier).body()
            ?: throw Exception("Show not found")
    }

    override suspend fun registerShowInterest(showId: String): Boolean {
        return showService.registerShowInterest(showId).body()?.hasInterest ?: false
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

    /** 알림 설정한 공연 목록 조회 ***/
    override suspend fun getAlarmReservedShow(size: Int, type: String): List<AlarmReservedShow> {
        return showService.getAlarmReservedShow(size, type).body()?.data ?: emptyList()
    }

}