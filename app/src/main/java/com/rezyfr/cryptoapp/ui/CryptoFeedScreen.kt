package com.rezyfr.cryptoapp.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rezyfr.cryptoapp.domain.CryptoFeed
import com.rezyfr.cryptoapp.domain.UiResult
import com.rezyfr.cryptoapp.presentation.CryptoFeedUiState
import com.rezyfr.cryptoapp.presentation.CryptoFeedViewModel

const val cryptoFeedRoute = "crypto_feed_route"

@Composable
fun CryptoFeedRoute(
    viewModel: CryptoFeedViewModel = viewModel(factory = CryptoFeedViewModel.Factory),
    onNavigateToCryptoDetails: (CryptoFeed) -> Unit
) {
    val cryptoFeedResult = viewModel.cryptoFeedUiState.collectAsState()
    CryptoFeedScreen(
        cryptoFeedResult = cryptoFeedResult.value,
        onNavigateToCryptoDetails = onNavigateToCryptoDetails
    )
}

@Composable
fun CryptoFeedScreen(
    cryptoFeedResult: CryptoFeedUiState,
    onNavigateToCryptoDetails: (CryptoFeed) -> Unit
) {
    when (cryptoFeedResult) {
        is CryptoFeedUiState.HasCryptoFeed -> {
            Log.d("CryptoFeedScreen", "Has crypto feed, ${cryptoFeedResult.cryptoFeed}")
        }
        is CryptoFeedUiState.NoCryptoFeed -> {
            Log.d("CryptoFeedScreen", "No crypto feed")
        }
    }
}