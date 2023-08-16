package com.rezyfr.cryptoapp.factories

import com.rezyfr.cryptoapp.domain.CryptoFeedRemoteUseCase

class CryptoFeedRemoteUseCaseFactory {
    companion object {
        fun createCryptoFeedRemoteUseCase(): CryptoFeedRemoteUseCase {
            return CryptoFeedRemoteUseCase(
                cryptoFeedHttpClient = CryptoFeedHttpClientFactory.createCryptoFeedHttpClient()
            )
        }
    }
}