package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.ApiResult
import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.alert.CheckAlertReservationResponse
import com.alreadyoccupiedseat.model.alert.TicketingAlertRequest
import com.alreadyoccupiedseat.model.show.InterestedData
import com.alreadyoccupiedseat.model.show.RegisterInterestResponse
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.model.show.Shows
import com.alreadyoccupiedseat.model.temp.AlertReservedShow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowService {

    @GET("api/v1/shows")
    suspend fun getEntireShow(
        @Query("sort") sort: String,
        @Query("onlyOpenSchedule") onlyOpenSchedule: Boolean,
        @Query("size") size: Int,
    ): Response<ApiResult<Shows>>

    @GET("api/v1/shows/interests")
    suspend fun getInterestedShowList(
        @Query("size") size: Int
    ): Response<ApiResult<PagingData<InterestedData>>>

    @GET("api/v1/shows/search")
    suspend fun searchShows(
        @Query("cursorId") cursorId: String? = null,
        @Query("size") size: Int,
        @Query("search") search: String
    ): Response<ApiResult<PagingData<SearchedShow>>>

    @GET("api/v1/shows/{showId}")
    suspend fun getShowDetail(
        @Path("showId") showId: String,
        @Header("Device-Token") deviceToken: String
    ): Response<ApiResult<ShowDetail>>

    @POST("api/v1/shows/{showId}/interests")
    suspend fun registerShowInterest(
        @Path("showId") showId: String
    ): Response<ApiResult<RegisterInterestResponse>>

    @POST("/api/v1/shows/{showId}/alert")
    suspend fun registerTicketingAlert(
        @Path("showId") showId: String,
        @Query("ticketingApiType") ticketingApiType: String,
        @Body alertTimes: TicketingAlertRequest
    ): Response<ApiResult<Unit>>

    @GET("api/v1/shows/{showId}/alert/reservations")
    suspend fun checkAlertReservation(
        @Path("showId") showId: String,
        @Query("ticketingApiType") ticketingApiType: String,
    ): Response<ApiResult<CheckAlertReservationResponse>>
    @GET("api/v1/shows/alerts")
    suspend fun getAlertReservedShow(
        @Query("size") size: Int,
        @Query("type") type: String
    ): Response<ApiResult<PagingData<AlertReservedShow>>>

}