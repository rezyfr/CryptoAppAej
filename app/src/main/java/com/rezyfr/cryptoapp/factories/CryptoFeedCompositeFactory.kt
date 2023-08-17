package com.rezyfr.cryptoapp.factories

import com.rezyfr.cryptoapp.domain.usecase.CryptoFeedRemoteWithLocalComposite
import com.rezyfr.cryptoapp.domain.CryptoFeedLoader

class CryptoFeedCompositeFactory {
    companion object {
        fun createCryptoFeedLoaderWithFallback(
            primary: CryptoFeedLoader,
            fallback: CryptoFeedLoader,
        ) : CryptoFeedLoader {
            return CryptoFeedRemoteWithLocalComposite(
                primary = primary,
                fallback = fallback,
            )
        }
    }
}