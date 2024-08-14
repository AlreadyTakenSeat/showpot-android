package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.login.LoginResponse
import com.alreadyoccupiedseat.model.login.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("api/v1/users/login")
    suspend fun  tryLogin(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}