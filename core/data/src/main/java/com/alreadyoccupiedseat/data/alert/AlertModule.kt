package com.alreadyoccupiedseat.data.alert

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlertModule {

    @Singleton
    @Binds
    abstract fun bindAlertDataSource(alertDataSourceImpl: AlertDataSourceImpl): AlertDataSource

    @Singleton
    @Binds
    abstract fun bindAlertRepository(alertRepositoryImpl: AlertRepositoryImpl): AlertRepository

}