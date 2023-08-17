package com.rezyfr.cryptoapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

const val nameArg = "name"
const val cryptoDetailsRoute = "crypto_detail_route/{$nameArg}"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoDetailScreen(
    name: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = name)
            }, navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            })
        }
    ) {
        Text(text = "Detail Screen", modifier = Modifier.padding(it))
    }
}