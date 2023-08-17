package com.rezyfr.cryptoapp.domain.usecase

import com.rezyfr.cryptoapp.domain.model.UiResult
import com.rezyfr.cryptoapp.domain.source.CryptoFeedLoader
import com.rezyfr.cryptoapp.domain.Result
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class CryptoFeedRemoteWithLocalComposite @Inject constructor(
    private val primary: CryptoFeedLoader,
    private val fallback: CryptoFeedLoader
) : CryptoFeedLoader {
    override fun load(): Result  = channelFlow {
        primary.load().collectLatest { remoteResult ->
            when (remoteResult) {
                is UiResult.Success -> {
                    trySend(remoteResult)
                }

                is UiResult.Error -> {
                    fallback.load().collectLatest { result ->
                        trySend(result)
                    }
                }

                is UiResult.Empty -> {
                    fallback.load().collectLatest { result ->
                        trySend(result)
                    }
                }

                else -> {
                    trySend(UiResult.Empty())
                }
            }
        }
    }
}