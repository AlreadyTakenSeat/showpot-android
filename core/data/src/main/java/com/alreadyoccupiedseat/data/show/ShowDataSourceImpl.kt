package com.alreadyoccupiedseat.data.show

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.alreadyoccupiedseat.data.getResult
import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.alert.TicketingAlertRequest
import com.alreadyoccupiedseat.model.alert.Times
import com.alreadyoccupiedseat.model.show.InterestedData
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.model.show.ShowPreview
import com.alreadyoccupiedseat.model.temp.AlertReservedShow
import com.alreadyoccupiedseat.network.ShowService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ShowDataSourceImpl @Inject constructor(
    private val showService: ShowService,
    @ApplicationContext private val context: Context,
) : ShowDataSource {

    override suspend fun getEntireShow(
        sort: String,
        onlyOpenSchedule: Boolean,
        size: Int,
        cursorId: String?,
    ): Result<PagingData<ShowPreview>> {

        return runCatching {
            showService.getEntireShow(
                sort = sort,
                onlyOpenSchedule = onlyOpenSchedule,
                size = size,
                cursorId = cursorId
            ).getResult {
                it.data
            }
        }
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
    override suspend fun getInterestedShowList(size: Int, cursorId: String?): Result<PagingData<InterestedData>> {
        return runCatching {
            showService.getInterestedShowList(size, cursorId).getResult {
                it.data
            }
        }
    }

    @SuppressLint("HardwareIds")
    override suspend fun getShowDetail(showId: String): ShowDetail {
        val viewIdentifier =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return showService.getShowDetail(showId, viewIdentifier).body()?.data
            ?: throw Exception("Show not found")
    }

    /** 관심 공연 등록, 취소 ***/
    override suspend fun registerShowInterest(showId: String): Result<Boolean> {
        return runCatching {
            showService.registerShowInterest(showId).getResult {
                it.code == 200
            }
        }
    }

    override suspend fun registerShowUnInterest(showId: String): Result<Boolean> {
        return runCatching {
            showService.registerShowUnInterest(showId).getResult {
                it.code == 200
            }
        }
    }

    override suspend fun registerTicketingAlert(
        showId: String,
        ticketingApiType: String,
        alertTimes: List<String>,
    ): Result<Unit> {
        return runCatching {
            showService.registerTicketingAlert(
                showId,
                ticketingApiType,
                TicketingAlertRequest(alertTimes)
            )
            // TODO Status Code 200이 아닌 경우 처리 -> 400 error 발생해도 isSuccess case 존재
        }
    }

    override suspend fun checkAlertReservation(
        showId: String,
        ticketingApiType: String,
    ): Times {
        return showService.checkAlertReservation(showId, ticketingApiType).body()?.data
            ?: throw Exception("Check Alert Reservation failed")
    }

    /** 알림 설정한 공연 목록 조회 ***/
    override suspend fun getAlertReservedShow(type: String, size: Int): List<AlertReservedShow> {
        return showService.getAlertReservedShow(type = type, size = size).body()?.data?.data ?: emptyList()
    }

    override suspend fun getAlertReservedShow(
        cursorId: String?,
        type: String,
        size: Int,
    ): Result<PagingData<AlertReservedShow>> {
        return runCatching {
            showService.getAlertReservedShow(cursorId = cursorId, type = type, size = size).getResult {
                it.data
            }
        }
    }
}