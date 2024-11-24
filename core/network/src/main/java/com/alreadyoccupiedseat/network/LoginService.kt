package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.ApiResult
import com.alreadyoccupiedseat.model.login.LoginResponse
import com.alreadyoccupiedseat.model.login.LoginRequest
import com.alreadyoccupiedseat.model.login.ProfileResponse
import com.alreadyoccupiedseat.model.login.TokenReIssueRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {

    @POST("api/v1/users/login")
    suspend fun tryLogin(
        @Body loginRequest: LoginRequest
    ): Response<ApiResult<LoginResponse>>

    @POST("api/v1/users/reissue")
    suspend fun reIssueToken(
        @Header("Refresh") refreshToken: String
    ): Response<ApiResult<LoginResponse>>

    @GET("api/v1/users/profile")
    suspend fun getProfile(): Response<ApiResult<ProfileResponse>>

    @POST("/api/v1/users/withdrawal")
    suspend fun requestWithDraw(
    ): Response<ApiResult<Unit>>
}