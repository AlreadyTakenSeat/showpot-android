package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.alert.CheckAlertReservationResponse
import com.alreadyoccupiedseat.model.alert.TicketingAlertRequest
import com.alreadyoccupiedseat.model.show.InterestedData
import com.alreadyoccupiedseat.model.show.RegisterInterestResponse
import com.alreadyoccupiedseat.model.show.ShowDetail
import com.alreadyoccupiedseat.model.show.Shows
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
    ): Response<Shows>

    @GET("api/v1/shows/interests")
    suspend fun getInterestedShowList(
        @Query("size") size: Int
    ): Response<PagingData<InterestedData>>

    @GET("api/v1/shows/search")
    suspend fun searchShows(
        @Query("cursorId") cursorId: String? = null,
        @Query("size") size: Int,
        @Query("search") search: String
    ): Response<PagingData<SearchedShow>>

    @GET("api/v1/shows/{showId}")
    suspend fun getShowDetail(
        @Path("showId") showId: String,
        @Header("viewIdentifier") viewIdentifier: String
    ): Response<ShowDetail>

    @POST("api/v1/shows/{showId}/interests")
    suspend fun registerShowInterest(
        @Path("showId") showId: String
    ): Response<RegisterInterestResponse>

    @POST("/api/v1/shows/{showId}/alert")
    suspend fun registerTicketingAlert(
        @Path("showId") showId: String,
        @Query("ticketingApiType") ticketingApiType: String,
        @Body alertTimes: TicketingAlertRequest
    ): Response<Unit>

    @POST("api/v1/shows/{showId}/alert/reservations")
    suspend fun checkAlertReservation(
        @Path("showId") showId: String,
        @Query("ticketingApiType") ticketingApiType: String,
    ): Response<CheckAlertReservationResponse>

}