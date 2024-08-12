package com.alreadyoccupiedseat.data.login.kakao

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.alreadyoccupiedseat.data.login.KakaoLoginDataSource
import com.alreadyoccupiedseat.data.login.LoginDataSource
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@KakaoLoginDataSource
class KakaoLoginDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LoginDataSource {
    override fun login() {

        val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "로그인 실패 $error")
            } else if (token != null) {
                Log.e(ContentValues.TAG, "로그인 성공 ${token.accessToken}")
            }
        }

        // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(ContentValues.TAG, "로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            context,
                            callback = mCallback
                        ) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    Log.e(ContentValues.TAG, "로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                context,
                callback = mCallback
            ) // 카카오 이메일 로그인
        }
    }
}