package com.rootstrap.android.ui.compose_navigation

import androidx.navigation.NavHostController
import com.rootstrap.domain.Product

object NavigationCallbacks {
    fun navigateToProductsList(navController: NavHostController) {
        navController.navigate(NavigationRoutes.ProductsList.route)
    }

    fun openProductDetail(navController: NavHostController, product: Product) {
        // TODO: navigate to product detail
    }
}
