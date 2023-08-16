package com.rezyfr.cryptoapp.http

import com.rezyfr.cryptoapp.data.RemoteRootCryptoFeed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface RetrofitClient {
    fun get(): Flow<HttpClientResult<RemoteRootCryptoFeed>>
}

class CryptoFeedHttpClient(
    private val api: CryptoFeedService
) : RetrofitClient {
    override fun get(): Flow<HttpClientResult<RemoteRootCryptoFeed>> = flow {
        try {
            val response = api.getCryptoFeed()
            emit(HttpClientResult.Success(response))
        } catch (e: Exception) {
            emit(HttpClientResult.Error(e))
        }
    }
}