package com.alreadyoccupiedseat.data.comment

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommentModule {

    @Singleton
    @Binds
    abstract fun bindCommentDataSource(artistDataSourceImpl: CommentDataSourceImpl): CommentDataSource

    @Singleton
    @Binds
    abstract fun bindCommentRepository(artistRepositoryImpl: CommentRepositoryImpl): CommentRepository

}