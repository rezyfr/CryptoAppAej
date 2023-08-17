package com.rezyfr.cryptoapp.domain.source

import com.rezyfr.cryptoapp.domain.Result

interface CryptoFeedLoader {
    fun load(): Result
}