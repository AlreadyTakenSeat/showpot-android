package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.Genre
import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.genre.SubscribeGenreRequest
import com.alreadyoccupiedseat.model.genre.SubscribeGenreResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GenreService {
    @GET("/api/v1/genres")
    suspend fun getGenres(
        @Query("size") size: Int = 20
    ): Response<PagingData<Genre>>

    @POST("/api/v1/genres/unsubscribe")
    suspend fun unsubscribeGenres(
        @Body genreIds: SubscribeGenreRequest
    ): Response<SubscribeGenreResponse>

    @POST("/api/v1/genres/subscribe")
    suspend fun subscribeGenres(
        @Body genreIds: SubscribeGenreRequest
    ): Response<SubscribeGenreResponse>

}