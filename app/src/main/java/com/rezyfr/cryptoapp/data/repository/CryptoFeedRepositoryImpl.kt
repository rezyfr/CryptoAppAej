package com.rezyfr.cryptoapp.data.repository

import com.rezyfr.cryptoapp.data.model.RemoteRootCryptoFeed
import com.rezyfr.cryptoapp.domain.repository.CryptoFeedRepository
import com.rezyfr.cryptoapp.http.CryptoFeedService
import com.rezyfr.cryptoapp.http.HttpClientResult
import execute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CryptoFeedRepositoryImpl @Inject constructor(
    private val api: CryptoFeedService
) : CryptoFeedRepository {
    override fun get(): Flow<HttpClientResult<RemoteRootCryptoFeed>> = flow {
        emit(execute { api.getCryptoFeed() })
    }.flowOn(Dispatchers.IO)
}