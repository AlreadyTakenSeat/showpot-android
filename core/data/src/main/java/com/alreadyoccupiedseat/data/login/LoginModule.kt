package com.alreadyoccupiedseat.data.login

import com.alreadyoccupiedseat.data.login.kakao.KakaoLoginDataSourceImpl
import com.alreadyoccupiedseat.data.login.kakao.KakaoLoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {

    @Singleton
    @Binds
    @KakaoLoginDataSource
    abstract fun bindKakaoLoginDataSource(kakaoLoginDataSourceImpl: KakaoLoginDataSourceImpl): LoginDataSource

    @Singleton
    @Binds
    @KakaoLoginRepository
    abstract fun bindKakaoLoginRepository(kakaoLoginRepositoryImpl: KakaoLoginRepositoryImpl): LoginRepository

}