package com.alreadyoccupiedseat.data.login

import android.content.Context
import com.alreadyoccupiedseat.model.login.ProfileResponse

interface LoginRepository {

    suspend fun kakaoLogin(activityContext: Context): Result<Unit>

    suspend fun googleLogin(): Result<Unit>

    suspend fun reIssueToken(): Result<Unit>

    suspend fun getProfile(): Result<ProfileResponse>

}