package com.alreadyoccupiedseat.data.show

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShowModule {

    @Singleton
    @Binds
    abstract fun bindShowDataSource(showDataSourceImpl: ShowDataSourceImpl): ShowDataSource

    @Singleton
    @Binds
    abstract fun bindShowRepository(showRepositoryImpl: ShowRepositoryImpl): ShowRepository

}