package com.rezyfr.cryptoapp.domain.di

import com.rezyfr.cryptoapp.domain.repository.CryptoFeedRepository
import com.rezyfr.cryptoapp.domain.source.CryptoFeedLoader
import com.rezyfr.cryptoapp.domain.usecase.CryptoFeedLocalUseCase
import com.rezyfr.cryptoapp.domain.usecase.CryptoFeedRemoteUseCase
import com.rezyfr.cryptoapp.domain.usecase.CryptoFeedRemoteWithLocalComposite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    @RemoteLoader
    fun provideCryptoFeedRemoteUseCase(
        repository: CryptoFeedRepository
    ): CryptoFeedLoader {
        return CryptoFeedRemoteUseCase(repository)
    }

    @Provides
    @LocalLoader
    fun provideCryptoFeedLocalUseCase(
        repository: CryptoFeedRepository
    ): CryptoFeedLoader {
        return CryptoFeedLocalUseCase(repository)
    }

    @Provides
    @CompositeLoader
    fun provideCryptoFeedRemoteWithLocalComposite(
        @RemoteLoader primary: CryptoFeedLoader,
        @LocalLoader fallback: CryptoFeedLoader,
    ): CryptoFeedLoader {
        return CryptoFeedRemoteWithLocalComposite(
            primary = primary,
            fallback = fallback,
        )
    }

}