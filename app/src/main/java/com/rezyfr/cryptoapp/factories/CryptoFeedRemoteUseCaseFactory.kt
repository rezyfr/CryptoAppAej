package com.rezyfr.cryptoapp.factories

import com.rezyfr.cryptoapp.domain.usecase.CryptoFeedRemoteUseCase

class CryptoFeedRemoteUseCaseFactory {
    companion object {
        fun createCryptoFeedRemoteUseCase(): CryptoFeedRemoteUseCase {
            return CryptoFeedRemoteUseCase(
                repository = CryptoFeedHttpClientFactory.createCryptoFeedHttpClient()
            )
        }
    }
}