package com.rezyfr.cryptoapp.domain.di

import com.rezyfr.cryptoapp.domain.usecase.CryptoFeedRemoteUseCase
import com.rezyfr.cryptoapp.domain.usecase.CryptoFeedRemoteWithLocalComposite
import com.rezyfr.cryptoapp.domain.CryptoFeedLoader
import com.rezyfr.cryptoapp.domain.repository.CryptoFeedRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CompositeLoader

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteLoader
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
    @CompositeLoader
    fun provideCryptoFeedRemoteWithLocalComposite(
        @RemoteLoader primary: CryptoFeedLoader,
        @RemoteLoader fallback: CryptoFeedLoader,
    ): CryptoFeedLoader {
        return CryptoFeedRemoteWithLocalComposite(
            primary = primary,
            fallback = fallback,
        )
    }
}