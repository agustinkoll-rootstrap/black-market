package com.rootstrap.android.ui.compose_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rootstrap.android.ui.products_list.ProductsList

@Composable
fun BlackMarketNavigationHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationRoutes.ProductsList.route
    ) {
        composable(NavigationRoutes.ProductsList.route) {
            ProductsList()
        }
    }
}
