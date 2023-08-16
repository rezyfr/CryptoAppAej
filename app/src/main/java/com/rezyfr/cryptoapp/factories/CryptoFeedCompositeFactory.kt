package com.rezyfr.cryptoapp.factories

import android.content.Context
import com.rezyfr.cryptoapp.domain.CryptoFeedRemoteWithLocalComposite
import com.rezyfr.cryptoapp.http.CryptoFeedLoader

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