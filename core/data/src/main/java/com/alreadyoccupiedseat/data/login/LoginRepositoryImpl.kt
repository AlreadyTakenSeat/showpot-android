package com.alreadyoccupiedseat.data.login

import android.content.Context
import com.alreadyoccupiedseat.data.login.remote.RemoteLoginDataSource
import com.alreadyoccupiedseat.model.login.ProfileResponse
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    @KakaoLoginDataSource private val kaKaoLoginDataSource: SocialLoginDataSource,
    private val remoteLoginDataSource: RemoteLoginDataSource
) : LoginRepository {

    private val SOCIAL_TYPE_KAKAO = "KAKAO"
    private val SOCIAL_TYPE_GOOGLE = "GOOGLE"

    override suspend fun kakaoLogin(activityContext: Context): Result<Unit> {
        return runCatching {
            kaKaoLoginDataSource.login(activityContext).onSuccess {
                remoteLoginDataSource.login(it, SOCIAL_TYPE_KAKAO)
            }.onFailure {
                throw Exception("카카오 로그인 실패")
            }
        }
    }

    override suspend fun googleLogin(): Result<Unit> {
        return runCatching {
            TODO("google Login")
        }
    }

    override suspend fun reIssueToken(): Result<Unit> {
        return remoteLoginDataSource.reIssueToken()
    }

    override suspend fun getProfile(): Result<ProfileResponse> {
        return remoteLoginDataSource.getProfile()
    }

    override suspend fun requestWithDraw(): Result<Unit> {
        return remoteLoginDataSource.requestWithDraw()
    }

}