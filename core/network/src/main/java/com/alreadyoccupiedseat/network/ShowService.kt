package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.show.ShowDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowService {

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

}