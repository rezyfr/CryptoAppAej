package com.rezyfr.cryptoapp.domain

import com.rezyfr.cryptoapp.http.CryptoFeedHttpClient
import com.rezyfr.cryptoapp.http.CryptoFeedLoader
import com.rezyfr.cryptoapp.http.HttpClientResult
import com.rezyfr.cryptoapp.http.Result
import kotlinx.coroutines.flow.channelFlow

class CryptoFeedRemoteUseCase (
    private val cryptoFeedHttpClient: CryptoFeedHttpClient
) : CryptoFeedLoader {
    override fun load(): Result  = channelFlow {
        cryptoFeedHttpClient.get().collect { result ->
            when (result) {
                is HttpClientResult.Success -> {
                    val cryptoFeed = result.data?.data
                    if (!cryptoFeed.isNullOrEmpty()) {
                        trySend(UiResult.Success(CryptoFeedItemsMapper.map(cryptoFeed)))
                    } else {
                        trySend(UiResult.Empty())
                    }
                }

                is HttpClientResult.Error -> {
                    when (result.throwable) {
                        is NoConnectivity -> {
                            trySend(UiResult.Error(NoConnectivity()))
                        }

                        is UnexpectedValueRepresentation -> {
                            trySend(UiResult.Error(UnexpectedValueRepresentation()))
                        }

                        is InvalidData -> {
                            trySend(UiResult.Error(InvalidData()))
                        }
                    }
                }
            }
        }
    }
}