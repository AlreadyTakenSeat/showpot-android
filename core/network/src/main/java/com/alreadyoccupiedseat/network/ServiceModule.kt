package com.alreadyoccupiedseat.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideArtistService(retrofit: Retrofit): ArtistService {
        return retrofit.create(ArtistService::class.java)
    }

    @Provides
    @Singleton
    fun provideAlertService(retrofit: Retrofit): AlertService {
        return retrofit.create(AlertService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideShowService(retrofit: Retrofit): ShowService {
        return retrofit.create(ShowService::class.java)
    }

    @Provides
    @Singleton
    fun provideGenreService(retrofit: Retrofit): GenreService {
        return retrofit.create(GenreService::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentService(retrofit: Retrofit): CommentService {
        return retrofit.create(CommentService::class.java)
    }

}