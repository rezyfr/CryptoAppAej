package com.rezyfr.cryptoapp.http

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideCryptoFeedService(retrofit: Retrofit): CryptoFeedService {
        return retrofit.create(CryptoFeedService::class.java)
    }
}