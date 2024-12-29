package com.alreadyoccupiedseat.data.login.kakao

import android.content.Context
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.data.login.KakaoLoginDataSource
import com.alreadyoccupiedseat.data.login.SocialLoginDataSource
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

@KakaoLoginDataSource
class KakaoLoginDataSourceImpl @Inject constructor() : SocialLoginDataSource {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun login(activityContext: Context): Result<String> {
        return suspendCancellableCoroutine { continuation ->
            val handleLoginResult: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                when {
                    error != null -> {
                        // 카카오톡 로그인은 가능하나, 카카오톡 계정 연결이 안되어있는 경우
                        if (error.toString().contains("302")){
                            UserApiClient.instance.loginWithKakaoAccount(activityContext) { token, error ->
                                if (error != null) {
                                    continuation.resumeWithException(Exception(error.message))
                                } else {
                                    continuation.resume(Result.success(token?.idToken ?: String.EMPTY)) {

                                    }
                                }

                            }
                        } else {
                            continuation.resumeWithException(Exception(error.message))
                        }

                    }
                    token != null -> {
                        continuation.resume(Result.success(token.idToken ?: String.EMPTY)) {

                        }
                    }
                    else -> {
                        continuation.resumeWithException(Exception("카카오 소셜 로그인 실패"))
                    }
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(activityContext)) {
                UserApiClient.instance.loginWithKakaoTalk(activityContext, callback = handleLoginResult)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(activityContext, callback = handleLoginResult)
            }
        }
    }
}