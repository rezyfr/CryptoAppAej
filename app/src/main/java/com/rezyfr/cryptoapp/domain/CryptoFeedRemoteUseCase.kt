package com.rezyfr.cryptoapp.domain

import com.rezyfr.cryptoapp.http.CryptoFeedHttpClient
import com.rezyfr.cryptoapp.http.CryptoFeedLoader
import com.rezyfr.cryptoapp.http.HttpClientResult
import com.rezyfr.cryptoapp.http.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class CryptoFeedRemoteUseCase (
    private val cryptoFeedHttpClient: CryptoFeedHttpClient
) : CryptoFeedLoader {
    override fun load(): Result  = flow {
        cryptoFeedHttpClient.get().collectLatest { result ->
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