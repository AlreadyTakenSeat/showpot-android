package com.alreadyoccupiedseat.data.login

interface LoginRepository {

    suspend fun kakaoLogin(): Result<Unit>

    suspend fun googleLogin(): Result<Unit>

}