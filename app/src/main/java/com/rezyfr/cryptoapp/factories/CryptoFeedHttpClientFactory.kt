package com.rezyfr.cryptoapp.factories

import com.rezyfr.cryptoapp.http.CryptoFeedHttpClient

class CryptoFeedHttpClientFactory {
    companion object {
        fun createCryptoFeedHttpClient(): CryptoFeedHttpClient {
            return CryptoFeedHttpClient(CryptoFeedServiceFactory.createCryptoFeedService())
        }
    }
}