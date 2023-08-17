package com.rezyfr.cryptoapp.domain.repository

import com.rezyfr.cryptoapp.data.model.RemoteRootCryptoFeed
import com.rezyfr.cryptoapp.http.HttpClientResult
import com.rezyfr.cryptoapp.persistence.entity.FeedEntity
import kotlinx.coroutines.flow.Flow

interface CryptoFeedRepository {
    fun getFromRemote(): Flow<HttpClientResult<RemoteRootCryptoFeed>>
    fun getFromLocal(): Flow<List<FeedEntity>>
    suspend fun insertAll(vararg feeds: FeedEntity)
}