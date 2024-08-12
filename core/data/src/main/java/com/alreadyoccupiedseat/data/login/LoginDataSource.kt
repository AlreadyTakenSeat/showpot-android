package com.alreadyoccupiedseat.data.login

interface LoginDataSource {

    suspend fun login(): String

}