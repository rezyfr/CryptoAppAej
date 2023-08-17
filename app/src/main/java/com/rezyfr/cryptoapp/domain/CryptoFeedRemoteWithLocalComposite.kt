package com.rezyfr.cryptoapp.domain

import com.rezyfr.cryptoapp.http.CryptoFeedLoader
import com.rezyfr.cryptoapp.http.Result
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class CryptoFeedRemoteWithLocalComposite (
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