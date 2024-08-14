package com.alreadyoccupiedseat.data.login.remote

import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.model.login.LoginRequest
import com.alreadyoccupiedseat.network.LoginService
import javax.inject.Inject

class RemoteLoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService,
    private val accountDataStore: AccountDataStore
) : RemoteLoginDataSource {
    override suspend fun login(identifier: String, socialType: String): Result<Unit> {
        return runCatching {
            val result = loginService.tryLogin(
                LoginRequest(
                    accountDataStore.getFcmToken() ?: String.EMPTY,
                    identifier,
                    socialType
                )
            ).body()

            accountDataStore.updateAccessToken(result?.accessToken ?: String.EMPTY)
            accountDataStore.updateRefreshToken(result?.refreshToken ?: String.EMPTY)
        }

    }
}