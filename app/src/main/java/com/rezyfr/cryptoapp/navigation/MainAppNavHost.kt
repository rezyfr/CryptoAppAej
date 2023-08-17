package com.rezyfr.cryptoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rezyfr.cryptoapp.ui.CryptoDetailScreen
import com.rezyfr.cryptoapp.ui.CryptoFeedRoute
import com.rezyfr.cryptoapp.ui.cryptoDetailsRoute
import com.rezyfr.cryptoapp.ui.cryptoFeedRoute
import com.rezyfr.cryptoapp.ui.nameArg

@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = cryptoFeedRoute
) {
    NavHost(navController = navController, modifier = modifier, startDestination = startDestination) {
        composable(cryptoFeedRoute) {
            CryptoFeedRoute(onNavigateToCryptoDetails = {
                navController.navigate(route = "$cryptoDetailsRoute/${it.coinInfo.name}")
            })
        }

        composable(
            route = "$cryptoDetailsRoute/{$nameArg}",
        ) { backStackEntry ->
            CryptoDetailScreen (
                name = backStackEntry.arguments?.getString(nameArg).orEmpty(),
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}