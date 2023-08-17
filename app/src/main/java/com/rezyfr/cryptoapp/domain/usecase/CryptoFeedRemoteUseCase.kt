package com.rezyfr.cryptoapp.domain.usecase

import com.rezyfr.cryptoapp.domain.CryptoFeedLoader
import com.rezyfr.cryptoapp.domain.Result
import com.rezyfr.cryptoapp.domain.mapper.CryptoFeedItemsMapper
import com.rezyfr.cryptoapp.domain.model.InvalidData
import com.rezyfr.cryptoapp.domain.model.NoConnectivity
import com.rezyfr.cryptoapp.domain.model.UiResult
import com.rezyfr.cryptoapp.domain.model.UnexpectedValueRepresentation
import com.rezyfr.cryptoapp.domain.repository.CryptoFeedRepository
import com.rezyfr.cryptoapp.http.HttpClientResult
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class CryptoFeedRemoteUseCase @Inject constructor(
    private val repository: CryptoFeedRepository
) : CryptoFeedLoader {
    override fun load(): Result  = channelFlow {
        repository.get().collect { result ->
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