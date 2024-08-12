package com.alreadyoccupiedseat.data.login.kakao

import com.alreadyoccupiedseat.data.login.KakaoLoginDataSource
import com.alreadyoccupiedseat.data.login.KakaoLoginRepository
import com.alreadyoccupiedseat.data.login.LoginDataSource
import com.alreadyoccupiedseat.data.login.LoginRepository
import javax.inject.Inject

@KakaoLoginRepository
class KakaoLoginRepositoryImpl @Inject constructor(
    @KakaoLoginDataSource private val kaKaoLoginDataSource: LoginDataSource
) : LoginRepository{
    override suspend fun login(): String {
        return kaKaoLoginDataSource.login()
    }
}