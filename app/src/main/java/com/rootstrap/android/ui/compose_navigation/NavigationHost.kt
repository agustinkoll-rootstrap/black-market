package com.rootstrap.android.ui.compose_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rootstrap.android.ui.dashboard.Dashboard
import com.rootstrap.android.ui.favourite.Favourite
import com.rootstrap.android.ui.login.LoginPage
import com.rootstrap.android.ui.product_detail.ProductDetail
import com.rootstrap.android.ui.products_list.ProductsList
import com.rootstrap.android.ui.shopping_cart.ShoppingCart
import com.rootstrap.android.ui.success.SuccessPage

@Composable
fun NavigationHost(navHostController: NavHostController, topBarVisibility: MutableState<Boolean>) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationRoutes.LoginPage.route
    ) {
        composable(NavigationRoutes.ProductsList.route) {
            topBarVisibility.value = true
            ProductsList()
        }
        composable(NavigationRoutes.Dashboard.route) {
            topBarVisibility.value = true
            Dashboard(navHostController)
        }
        composable(NavigationRoutes.Favourite.route) {
            topBarVisibility.value = true
            Favourite()
        }
        composable(NavigationRoutes.ShoppingCart.route) {
            topBarVisibility.value = true
            ShoppingCart()
        }
        composable(NavigationRoutes.LoginPage.route){
            topBarVisibility.value = false
            LoginPage(navHostController)
        }
        composable(
            route = NavigationRoutes.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            topBarVisibility.value = false
            ProductDetail(navBackStackEntry.arguments?.getInt("productId") ?: -1, navHostController)
        }
        composable(
            route = NavigationRoutes.SuccessPage.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            topBarVisibility.value = true
            SuccessPage(navBackStackEntry.arguments?.getInt("productId") ?: -1)
        }
    }
}
