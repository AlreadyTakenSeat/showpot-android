package com.alreadyoccupiedseat.data.login.remote

interface RemoteLoginDataSource {

    suspend fun login(identifier: String, socialType: String): Result<Unit>
}