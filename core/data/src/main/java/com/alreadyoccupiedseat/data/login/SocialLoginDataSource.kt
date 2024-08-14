package com.alreadyoccupiedseat.data.login

interface SocialLoginDataSource {

    suspend fun login(): String

}