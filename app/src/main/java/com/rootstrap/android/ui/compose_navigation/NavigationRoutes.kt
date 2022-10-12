package com.rootstrap.android.ui.compose_navigation

import com.rootstrap.android.R

sealed class NavigationRoutes(val route: String, var title: String, var icon: Int?) {
    object Login : NavigationRoutes("login", "", null)
    object ProductsList : NavigationRoutes("products_list", "", null)
    object Dashboard : NavigationRoutes("dashboard", "Home", R.drawable.ic_home )
    object ShoppingCart : NavigationRoutes("shopping_cart", "Cart", R.drawable.ic_shopping)
    object Favourite : NavigationRoutes("favourite", "Favourites", R.drawable.ic_favorite)
}
