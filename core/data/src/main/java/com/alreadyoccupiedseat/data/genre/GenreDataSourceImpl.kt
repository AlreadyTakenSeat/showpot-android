package com.alreadyoccupiedseat.data.genre

import com.alreadyoccupiedseat.model.Genre
import com.alreadyoccupiedseat.model.genre.SubscribeGenreRequest
import com.alreadyoccupiedseat.network.GenreService
import javax.inject.Inject

class GenreDataSourceImp @Inject constructor(
    private val genreService: GenreService,
) : GenreDataSource {
    override suspend fun getGenres(
        size: Int,
    ): List<Genre> {
        return genreService.getGenres(size).body()?.data?.data ?: emptyList()
    }

    override suspend fun unsubscribeGenres(genreIds: List<String>): List<String> {
        return genreService.unsubscribeGenres(SubscribeGenreRequest(genreIds)).body()?.data?.successSubscriptionGenreIds
            ?: emptyList()
    }

    override suspend fun subscribeGenres(genreIds: List<String>): List<String> {
        return genreService.subscribeGenres(SubscribeGenreRequest(genreIds))
            .body()?.data?.successSubscriptionGenreIds ?: emptyList()
    }

}