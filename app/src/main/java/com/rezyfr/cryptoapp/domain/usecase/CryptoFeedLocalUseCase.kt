package com.rezyfr.cryptoapp.domain.usecase

import com.rezyfr.cryptoapp.domain.source.CryptoFeedLoader
import com.rezyfr.cryptoapp.domain.Result
import com.rezyfr.cryptoapp.domain.mapper.CryptoFeedItemsMapper
import com.rezyfr.cryptoapp.domain.model.UiResult
import com.rezyfr.cryptoapp.domain.repository.CryptoFeedRepository
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class CryptoFeedLocalUseCase @Inject constructor(
    private val repository: CryptoFeedRepository
) : CryptoFeedLoader {
    override fun load(): Result  = channelFlow {
        repository.getFromLocal().collect { result ->
            if (result.isNotEmpty()) {
                val resultDomain = CryptoFeedItemsMapper.mapLocalToDomain(result)
                trySend(UiResult.Success(resultDomain))
            } else {
                trySend(UiResult.Empty())
            }
        }
    }
}