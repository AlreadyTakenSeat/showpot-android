package com.alreadyoccupiedseat.data.artist

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ArtistModule {

    @Singleton
    @Binds
    abstract fun bindArtistDataSource(artistDataSourceImpl: ArtistDataSourceImpl): ArtistDataSource

    @Singleton
    @Binds
    abstract fun bindArtistRepository(artistRepositoryImpl: ArtistRepositoryImpl): ArtistRepository

}