package com.alreadyoccupiedseat.data.genre

import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreDataSource: GenreDataSource
): GenreRepository {
    override suspend fun getGenres(
        size: Int
    ) = genreDataSource.getGenres(size)

}