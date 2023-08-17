package com.rezyfr.cryptoapp.factories

import com.rezyfr.cryptoapp.data.repository.CryptoFeedRepositoryImpl

class CryptoFeedHttpClientFactory {
    companion object {
        fun createCryptoFeedHttpClient(): CryptoFeedRepositoryImpl {
            return CryptoFeedRepositoryImpl(CryptoFeedServiceFactory.createCryptoFeedService())
        }
    }
}