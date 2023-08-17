package com.rezyfr.cryptoapp.data.repository

import com.rezyfr.cryptoapp.data.model.RemoteRootCryptoFeed
import com.rezyfr.cryptoapp.domain.repository.CryptoFeedRepository
import com.rezyfr.cryptoapp.http.CryptoFeedService
import com.rezyfr.cryptoapp.http.HttpClientResult
import com.rezyfr.cryptoapp.persistence.dao.FeedDao
import com.rezyfr.cryptoapp.persistence.entity.FeedEntity
import execute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CryptoFeedRepositoryImpl @Inject constructor(
    private val api: CryptoFeedService,
    private val feedDao: FeedDao
) : CryptoFeedRepository {
    override fun getFromRemote(): Flow<HttpClientResult<RemoteRootCryptoFeed>> = flow {
        emit(execute { api.getCryptoFeed() })
    }.flowOn(Dispatchers.IO)
    override fun getFromLocal(): Flow<List<FeedEntity>> =
        feedDao.getAll().flowOn(Dispatchers.IO)

    override suspend fun insertAll(vararg feeds: FeedEntity) {
        feedDao.insertAll(*feeds)
    }
}