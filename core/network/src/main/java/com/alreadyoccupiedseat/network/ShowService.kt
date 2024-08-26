package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.show.ShowDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ShowService {

    @GET("api/v1/shows/{showId}")
    suspend fun getShowDetail(
        @Path("showId") showId: String,
        @Header("viewIdentifier") viewIdentifier: String
    ): Response<ShowDetail>

}