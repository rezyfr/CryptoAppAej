package com.rezyfr.cryptoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rezyfr.cryptoapp.ui.CryptoFeedRoute
import com.rezyfr.cryptoapp.ui.cryptoFeedRoute

@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = cryptoFeedRoute
) {
    NavHost(navController = navController, modifier = modifier, startDestination = startDestination) {
        composable(cryptoFeedRoute) {
            CryptoFeedRoute(onNavigateToCryptoDetails = {})
        }
    }
}