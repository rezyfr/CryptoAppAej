package com.rezyfr.cryptoapp.http

import com.rezyfr.cryptoapp.data.model.RemoteRootCryptoFeed
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoFeedService {
    @GET("data/top/totaltoptiervolfull")
    suspend fun getCryptoFeed(
        @Query("limit") limit: Int = 10,
        @Query("tsym") tsym: String = "USD"
    ): RemoteRootCryptoFeed
}