package com.alreadyoccupiedseat.data.login

interface LoginRepository {

    suspend fun login(): String

}