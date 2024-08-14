package com.alreadyoccupiedseat.data.login

import com.alreadyoccupiedseat.data.login.remote.RemoteLoginDataSource
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    @KakaoLoginDataSource private val kaKaoLoginDataSource: SocialLoginDataSource,
    private val remoteLoginDataSource: RemoteLoginDataSource
) : LoginRepository {

    private val SOCIAL_TYPE_KAKAO = "KAKAO"
    private val SOCIAL_TYPE_GOOGLE = "GOOGLE"

    override suspend fun kakaoLogin(): Result<Unit> {
        val kakaoIdentifier = kaKaoLoginDataSource.login()
        return remoteLoginDataSource.login(kakaoIdentifier, SOCIAL_TYPE_KAKAO)
    }

    override suspend fun googleLogin(): Result<Unit> {
        return runCatching {
            TODO("google Login")
        }
    }

}