package com.rezyfr.cryptoapp.domain

import com.rezyfr.cryptoapp.data.RemoteCryptoFeedItem

class CryptoFeedItemsMapper {
    companion object {
        fun map(
            remoteCryptoFeed: List<RemoteCryptoFeedItem>
        ): List<CryptoFeed> {
            return remoteCryptoFeed.map { remoteCryptoFeedItem ->
                CryptoFeed(
                    coinInfo = CoinInfo(
                        id = remoteCryptoFeedItem.coinInfo.id,
                        name = remoteCryptoFeedItem.coinInfo.name,
                        fullName = remoteCryptoFeedItem.coinInfo.fullName,
                        imageUrl = remoteCryptoFeedItem.coinInfo.imageUrl
                    ),
                    display = Display(
                        usd = Usd(
                            price = remoteCryptoFeedItem.raw.usd.price,
                            changePctDay = remoteCryptoFeedItem.raw.usd.changePctDay
                        )
                    )
                )
            }
        }
    }
}