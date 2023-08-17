package com.rezyfr.cryptoapp.domain.usecase

import com.rezyfr.cryptoapp.domain.source.CryptoFeedLoader
import com.rezyfr.cryptoapp.domain.Result
import com.rezyfr.cryptoapp.domain.mapper.CryptoFeedItemsMapper
import com.rezyfr.cryptoapp.domain.model.CryptoFeed
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
        repository.getFromRemote().collect { result ->
            when (result) {
                is HttpClientResult.Success -> {
                    val cryptoFeed = result.data?.data
                    if (!cryptoFeed.isNullOrEmpty()) {
                        CryptoFeedItemsMapper.mapRemoteToDomain(cryptoFeed).also {
                            insertToLocalDb(it)
                            trySend(UiResult.Success(it))
                        }
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

                        else -> {
                            trySend(UiResult.Error(result.throwable ?: Throwable("Unkonwn error")))
                        }
                    }
                }
            }
        }
    }

    private suspend fun insertToLocalDb(feed: List<CryptoFeed>) {
        repository.insertAll(*CryptoFeedItemsMapper.mapToEntity(feed).toTypedArray())
    }
}