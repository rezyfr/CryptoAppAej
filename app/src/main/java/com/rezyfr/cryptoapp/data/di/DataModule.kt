package com.rezyfr.cryptoapp.data.di

import com.rezyfr.cryptoapp.data.repository.CryptoFeedRepositoryImpl
import com.rezyfr.cryptoapp.domain.repository.CryptoFeedRepository
import com.rezyfr.cryptoapp.http.CryptoFeedService
import com.rezyfr.cryptoapp.persistence.dao.FeedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideCryptoFeedRepository(
        service: CryptoFeedService,
        feedDao: FeedDao
    ): CryptoFeedRepository {
        return CryptoFeedRepositoryImpl(service, feedDao)
    }
}