package com.rootstrap.android.ui.compose_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rootstrap.android.ui.dashboard.Dashboard
import com.rootstrap.android.ui.favourite.Favourite
import com.rootstrap.android.ui.products_list.ProductsList
import com.rootstrap.android.ui.shopping_cart.ShoppingCart

@Composable
fun NavigationHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationRoutes.Dashboard.route
    ) {
        composable(NavigationRoutes.ProductsList.route) {
            ProductsList()
        }
        composable(NavigationRoutes.Dashboard.route) {
            Dashboard()
        }
        composable(NavigationRoutes.Favourite.route) {
            Favourite()
        }
        composable(NavigationRoutes.ShoppingCart.route) {
            ShoppingCart()
        }
    }
}