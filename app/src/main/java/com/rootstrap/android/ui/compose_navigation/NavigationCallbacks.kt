package com.rootstrap.android.ui.compose_navigation

import androidx.navigation.NavHostController

object NavigationCallbacks {
    fun navigateToProductsList(navController: NavHostController) {
        navController.navigate(NavigationRoutes.ProductsList.route)
    }
}
