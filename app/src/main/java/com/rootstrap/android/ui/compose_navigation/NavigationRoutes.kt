package com.rootstrap.android.ui.compose_navigation

import androidx.annotation.DrawableRes
import com.rootstrap.android.R

sealed class NavigationRoutes(
    val route: String,
    var title: String = "",
    @DrawableRes var icon: Int? = null,
) {
    object Login : NavigationRoutes(route = "login")
    object ProductsList :
        NavigationRoutes("dashboard/products_list")

    object Dashboard : NavigationRoutes("dashboard", "Home", R.drawable.ic_home)
    object ShoppingCart :
        NavigationRoutes("shopping_cart", "Cart", R.drawable.ic_shopping)

    object Favourite :
        NavigationRoutes("favourite", "Favourites", R.drawable.ic_favorite)

    object ProductDetail :
        NavigationRoutes("dashboard/product_detail/{productId}") {
        fun getProductIdRoute(productId: Int) = "dashboard/product_detail/$productId"
    }

    object SuccessPage : NavigationRoutes(route = "success/{productId}") {
        fun getProductIdRoute(productId: Int) = "success/$productId"
    }
}
