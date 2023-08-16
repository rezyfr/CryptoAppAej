package com.rezyfr.cryptoapp.http

import com.rezyfr.cryptoapp.domain.CryptoFeed
import com.rezyfr.cryptoapp.domain.CryptoFeedItemsMapper
import com.rezyfr.cryptoapp.domain.InvalidData
import com.rezyfr.cryptoapp.domain.NoConnectivity
import com.rezyfr.cryptoapp.domain.UiResult
import com.rezyfr.cryptoapp.domain.UnexpectedValueRepresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

typealias Result = Flow<UiResult<List<CryptoFeed>>>

interface CryptoFeedLoader {
    fun load(): Result
}

class RemoteCryptoFeedLoader constructor(
    private val httpClient: CryptoFeedHttpClient
) : CryptoFeedLoader {
    override fun load(): Result = flow {
        httpClient.get().collectLatest { result ->
            when (result) {
                is HttpClientResult.Success -> {
                    val cryptoFeed = result.data?.data
                    if (!cryptoFeed.isNullOrEmpty()) {
                        emit(UiResult.Success(CryptoFeedItemsMapper.map(cryptoFeed)))
                    } else {
                        emit(UiResult.Empty())
                    }
                }

                is HttpClientResult.Error -> {
                    when (result.throwable) {
                        is NoConnectivity -> {
                            emit(UiResult.Error(NoConnectivity()))
                        }

                        is UnexpectedValueRepresentation -> {
                            emit(UiResult.Error(UnexpectedValueRepresentation()))
                        }

                        is InvalidData -> {
                            emit(UiResult.Error(InvalidData()))
                        }
                    }
                }
            }
        }
    }
}