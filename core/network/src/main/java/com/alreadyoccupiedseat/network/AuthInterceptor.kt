package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.datastore.AccountDataStore
import okhttp3.Interceptor

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

// reference: https://github.com/square/okhttp/issues/7164
@Suppress("potential danger of runBlocking")
interface CoroutineInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            try {
                interceptSuspend(chain)
            } catch (ce: CancellationException) {
                throw IOException(ce)
            }
        }
    }

    suspend fun interceptSuspend(chain: Interceptor.Chain): Response
}

class AuthInterceptor @Inject constructor(
    private val dataStore: AccountDataStore,
) : CoroutineInterceptor {

    override suspend fun interceptSuspend(chain: Interceptor.Chain): Response {

        val accessToken = dataStore.getAccessToken()

        val token = if (accessToken != null) "Bearer $accessToken" else ""
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}