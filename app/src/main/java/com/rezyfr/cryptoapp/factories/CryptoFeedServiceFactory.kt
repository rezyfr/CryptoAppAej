package com.rezyfr.cryptoapp.factories

import com.rezyfr.cryptoapp.http.CryptoFeedService
import com.rezyfr.cryptoapp.http.HttpFactory

class CryptoFeedServiceFactory {
    companion object {
        fun createCryptoFeedService(): CryptoFeedService {
            return HttpFactory.createRetrofit(
                HttpFactory.createMoshi(),
                HttpFactory.createOkHttpClient(
                    HttpFactory.createLoggingInterceptor(),
                )
            ).create(CryptoFeedService::class.java)
        }
    }
}