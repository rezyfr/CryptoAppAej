package com.rezyfr.cryptoapp.domain

import com.rezyfr.cryptoapp.http.CryptoFeedLoader
import com.rezyfr.cryptoapp.http.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class CryptoFeedRemoteWithLocalComposite (
    private val primary: CryptoFeedLoader,
    private val fallback: CryptoFeedLoader
) : CryptoFeedLoader {
    override fun load(): Result  = flow {
        primary.load().collectLatest { remoteResult ->
            when (remoteResult) {
                is UiResult.Success -> {
                    emit(remoteResult)
                }

                is UiResult.Error -> {
                    fallback.load().collectLatest { result ->
                        emit(result)
                    }
                }

                is UiResult.Empty -> {
                    fallback.load().collectLatest { result ->
                        emit(result)
                    }
                }

                else -> {
                    emit(UiResult.Empty())
                }
            }
        }
    }
}