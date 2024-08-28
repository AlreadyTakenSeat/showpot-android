package com.alreadyoccupiedseat.data.genre

import com.alreadyoccupiedseat.model.Genre
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreDataSource: GenreDataSource
): GenreRepository {
    override suspend fun getGenres(
        size: Int
    ): List<Genre> {
      return genreDataSource.getGenres(size)
    }

    override suspend fun unsubscribeGenres(genreIds: List<String>): List<String> {
        return genreDataSource.unsubscribeGenres(genreIds)
    }

    override suspend fun subscribeGenres(genreIds: List<String>): List<String> {
        return genreDataSource.subscribeGenres(genreIds)
    }
}