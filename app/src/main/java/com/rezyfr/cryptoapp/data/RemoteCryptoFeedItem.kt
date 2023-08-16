package com.rezyfr.cryptoapp.data

import com.squareup.moshi.Json

data class RemoteRootCryptoFeed(
    @Json(name = "Data")
    val data: List<RemoteCryptoFeedItem>
)

data class RemoteCryptoFeedItem(
    @Json(name = "CoinInfo")
    val coinInfo: RemoteCoinInfo,
    @Json(name = "DISPLAY")
    val raw: RemoteDisplay
)

data class RemoteCoinInfo(
    @Json(name = "Id")
    val id: String,
    @Json(name = "Name")
    val name: String,
    @Json(name = "FullName")
    val fullName: String
)

data class RemoteDisplay (
    @Json(name = "USD")
    val usd: RemoteUsd
)

data class RemoteUsd(
    @Json(name = "PRICE")
    val price: Double,
    @Json(name = "CHANGEPCTDAY")
    val changePctDay: Long
)
