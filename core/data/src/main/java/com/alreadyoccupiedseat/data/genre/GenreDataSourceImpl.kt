package com.alreadyoccupiedseat.data.genre

import com.alreadyoccupiedseat.model.Genre
import com.alreadyoccupiedseat.network.GenreService
import javax.inject.Inject

class GenreDataSourceImp @Inject constructor(
    private val genreService: GenreService
): GenreDataSource {
    override suspend fun getGenres(
        size: Int
    ): List<Genre> {
        return genreService.getGenres(size).body()?.data ?: emptyList()
    }

}