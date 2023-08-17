package com.rezyfr.cryptoapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rezyfr.cryptoapp.domain.CryptoFeed
import com.rezyfr.cryptoapp.domain.CryptoFeedRemoteUseCase
import com.rezyfr.cryptoapp.domain.UiResult
import com.rezyfr.cryptoapp.factories.CryptoFeedCompositeFactory
import com.rezyfr.cryptoapp.factories.CryptoFeedRemoteUseCaseFactory
import com.rezyfr.cryptoapp.http.CryptoFeedLoader
import com.rezyfr.cryptoapp.presentation.CryptoFeedUiState.Initial.isLoading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface CryptoFeedUiState {
    val isLoading: Boolean
    val failed: String

    object Initial : CryptoFeedUiState {
        override val isLoading: Boolean = true
        override val failed: String = ""
    }
    data class HasCryptoFeed(
        val cryptoFeed: List<CryptoFeed>,
        override val isLoading: Boolean = false,
        override val failed: String = ""
    ) : CryptoFeedUiState

    data class NoCryptoFeed(
        override val isLoading: Boolean = false,
        override val failed: String = ""
    ) : CryptoFeedUiState
}

class CryptoFeedViewModel constructor(
    private val cryptoFeedLoader: CryptoFeedLoader,
) : ViewModel() {

    private val viewModelState = MutableStateFlow<CryptoFeedUiState>(CryptoFeedUiState.Initial)

    val cryptoFeedUiState = viewModelState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CryptoFeedUiState.Initial
        )

    init {
        refreshCryptoFeed()
    }

    fun refreshCryptoFeed() {
        viewModelScope.launch {
            cryptoFeedLoader.load().collectLatest { result ->
                Log.d("CryptoFeed", "$result")
                viewModelState.update {
                    when (result) {
                        is UiResult.Loading -> CryptoFeedUiState.Initial
                        is UiResult.Empty -> CryptoFeedUiState.NoCryptoFeed(isLoading = false, failed = "Empty")
                        is UiResult.Error -> CryptoFeedUiState.NoCryptoFeed(isLoading = false, failed = result.throwable?.message.orEmpty())
                        is UiResult.Success -> CryptoFeedUiState.HasCryptoFeed(cryptoFeed = result.data.orEmpty(), isLoading = false)
                    }
                }
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CryptoFeedViewModel(
                    cryptoFeedLoader = CryptoFeedCompositeFactory.createCryptoFeedLoaderWithFallback(
                        primary = CryptoFeedRemoteUseCaseFactory.createCryptoFeedRemoteUseCase(),
                        fallback = CryptoFeedRemoteUseCaseFactory.createCryptoFeedRemoteUseCase()
                    )
                )
            }
        }
    }
}