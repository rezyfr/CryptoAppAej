package com.rezyfr.cryptoapp.domain

data class CryptoFeed(
    val coinInfo: CoinInfo,
    val display: Display
)

data class CoinInfo(
    val id: String,
    val name: String,
    val fullName: String,
    val imageUrl: String
)

data class Display(
    val usd: Usd
)

data class Usd(
    val price: Double,
    val changePctDay: Float
)
