package com.rezyfr.cryptoapp.domain.repository

import com.rezyfr.cryptoapp.data.model.RemoteRootCryptoFeed
import com.rezyfr.cryptoapp.http.HttpClientResult
import kotlinx.coroutines.flow.Flow

interface CryptoFeedRepository {
    fun get(): Flow<HttpClientResult<RemoteRootCryptoFeed>>
}