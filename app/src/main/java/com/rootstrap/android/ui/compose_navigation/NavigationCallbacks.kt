package com.rootstrap.android.ui.compose_navigation

import androidx.navigation.NavHostController
import com.rootstrap.domain.Product

object NavigationCallbacks {
    fun navigateToProductsList(navController: NavHostController) {
        navController.navigate(NavigationRoutes.ProductsList.route)
    }

    fun openProductDetail(navController: NavHostController, product: Product) {
        navController.navigate(NavigationRoutes.ProductDetail.getProductIdRoute(product.id))
    }

    fun navigateToSuccessPage(navController: NavHostController, product: Product) {
        navController.navigate(NavigationRoutes.SuccessPage.getProductIdRoute(product.id))
    }
}
