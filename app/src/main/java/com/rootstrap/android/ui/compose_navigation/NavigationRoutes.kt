package com.rootstrap.android.ui.compose_navigation

sealed class NavigationRoutes(val route: String) {
    object Login : NavigationRoutes("login")

    object ProductsList : NavigationRoutes("products_list")
}
