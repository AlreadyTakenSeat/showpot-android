package com.alreadyoccupiedseat.data.genre

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GenreModule {

    @Singleton
    @Binds
    abstract fun bindGenreDataSource(genreDataSourceImpl: GenreDataSourceImp): GenreDataSource

    @Singleton
    @Binds
    abstract fun bindGenreRepository(genreRepositoryImpl: GenreRepositoryImpl): GenreRepository

}