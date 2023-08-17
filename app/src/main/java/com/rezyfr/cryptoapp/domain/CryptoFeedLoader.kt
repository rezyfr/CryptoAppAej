package com.rezyfr.cryptoapp.domain

import com.rezyfr.cryptoapp.domain.model.CryptoFeed
import com.rezyfr.cryptoapp.domain.model.UiResult
import kotlinx.coroutines.flow.Flow

typealias Result = Flow<UiResult<List<CryptoFeed>>>

