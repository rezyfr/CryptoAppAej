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

data class CryptoFeedViewModelState(
    val isLoading: Boolean = false,
    val cryptoFeed: List<CryptoFeed> = emptyList(),
    val failed: String = ""
) {
    fun toCryptoFeedUiState() : CryptoFeedUiState {
        return if (cryptoFeed.isNotEmpty()) {
            CryptoFeedUiState.HasCryptoFeed(
                cryptoFeed = cryptoFeed,
                isLoading = isLoading,
                failed = failed
            )
        } else {
            CryptoFeedUiState.NoCryptoFeed(
                isLoading = isLoading,
                failed = failed
            )
        }
    }
}

class CryptoFeedViewModel constructor(
    private val cryptoFeedLoader: CryptoFeedLoader,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        CryptoFeedViewModelState(
            isLoading = true
        )
    )

    val cryptoFeedUiState = viewModelState.map(CryptoFeedViewModelState::toCryptoFeedUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = viewModelState.value.toCryptoFeedUiState()
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
                        is UiResult.Loading -> it.copy(isLoading = true)
                        is UiResult.Empty -> it.copy(cryptoFeed = emptyList(), isLoading = false)
                        is UiResult.Error -> it.copy(failed = result.throwable?.message.orEmpty(), isLoading = false)
                        is UiResult.Success -> it.copy(cryptoFeed = result.data.orEmpty(), isLoading = false)
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