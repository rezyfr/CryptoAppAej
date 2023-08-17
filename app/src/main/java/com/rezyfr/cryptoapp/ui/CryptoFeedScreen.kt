package com.rezyfr.cryptoapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rezyfr.cryptoapp.domain.model.CryptoFeed
import com.rezyfr.cryptoapp.presentation.CryptoFeedUiState
import com.rezyfr.cryptoapp.presentation.CryptoFeedViewModel
import com.rezyfr.cryptoapp.ui.components.CryptoFeedList

const val cryptoFeedRoute = "crypto_feed_route"
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CryptoFeedRoute(
    viewModel: CryptoFeedViewModel = hiltViewModel(),
    onNavigateToCryptoDetails: (CryptoFeed) -> Unit
) {
    val cryptoFeedResult by viewModel.cryptoFeedUiState.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(refreshing = cryptoFeedResult.isLoading, onRefresh = {
            viewModel.refreshCryptoFeed()
        })
    CryptoFeedScreen(
        cryptoFeedResult = cryptoFeedResult,
        onNavigateToCryptoDetails = onNavigateToCryptoDetails,
        pullRefreshState = pullRefreshState
    )
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CryptoFeedScreen(
    cryptoFeedResult: CryptoFeedUiState,
    onNavigateToCryptoDetails: (CryptoFeed) -> Unit,
    pullRefreshState: PullRefreshState
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Crypto") })
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(pullRefreshState)
        ) {
            when (cryptoFeedResult) {
                is CryptoFeedUiState.HasCryptoFeed -> {
                    CryptoFeedList(
                        cryptoFeed = cryptoFeedResult.cryptoFeed,
                        navigateToDetails = onNavigateToCryptoDetails
                    )
                }

                is CryptoFeedUiState.NoCryptoFeed -> {
                    EmptyScreen(message = cryptoFeedResult.failed.ifEmpty { "Empty" })
                }

                is CryptoFeedUiState.Initial -> {
                    EmptyScreen(message = "Loading data")
                }
            }

            if (cryptoFeedResult !is CryptoFeedUiState.Initial) {
                PullRefreshIndicator(
                    refreshing = cryptoFeedResult.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(
                        Alignment.TopCenter
                    )
                )
            }
        }
    }
}
@Composable
fun EmptyScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(message, modifier = Modifier.align(Alignment.Center))
    }
}