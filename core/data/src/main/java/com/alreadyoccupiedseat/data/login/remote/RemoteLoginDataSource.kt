package com.alreadyoccupiedseat.data.login.remote

import com.alreadyoccupiedseat.model.login.ProfileResponse

interface RemoteLoginDataSource {

    suspend fun login(identifier: String, socialType: String): Result<Unit>

    suspend fun reIssueToken(): Result<Unit>

    suspend fun getProfile(): Result<ProfileResponse>
}