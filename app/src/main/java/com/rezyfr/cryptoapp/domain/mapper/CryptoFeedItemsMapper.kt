package com.rezyfr.cryptoapp.domain.mapper

import com.rezyfr.cryptoapp.data.model.RemoteCryptoFeedItem
import com.rezyfr.cryptoapp.domain.model.CoinInfo
import com.rezyfr.cryptoapp.domain.model.CryptoFeed
import com.rezyfr.cryptoapp.domain.model.Display
import com.rezyfr.cryptoapp.domain.model.Usd
import com.rezyfr.cryptoapp.persistence.entity.FeedEntity

class CryptoFeedItemsMapper {
    companion object {
        fun mapRemoteToDomain(
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
        fun mapLocalToDomain(
            entityCryptoFeed: List<FeedEntity>
        ): List<CryptoFeed> {
            return entityCryptoFeed.map { remoteCryptoFeedItem ->
                CryptoFeed(
                    coinInfo = CoinInfo(
                        id = remoteCryptoFeedItem.id,
                        name = remoteCryptoFeedItem.name,
                        fullName = remoteCryptoFeedItem.fullName,
                        imageUrl = remoteCryptoFeedItem.imageUrl
                    ),
                    display = Display(
                        usd = Usd(
                            price = remoteCryptoFeedItem.price,
                            changePctDay = remoteCryptoFeedItem.changePctDay
                        )
                    )
                )
            }
        }
        fun mapToEntity(
            domainCryptoFeed: List<CryptoFeed>
        ) : List<FeedEntity> {
            return domainCryptoFeed.map { domainCryptoFeedItem ->
                FeedEntity(
                    id = domainCryptoFeedItem.coinInfo.id,
                    name = domainCryptoFeedItem.coinInfo.name,
                    fullName = domainCryptoFeedItem.coinInfo.fullName,
                    imageUrl = domainCryptoFeedItem.coinInfo.imageUrl,
                    price = domainCryptoFeedItem.display.usd.price,
                    changePctDay = domainCryptoFeedItem.display.usd.changePctDay
                )
            }
        }
    }
}