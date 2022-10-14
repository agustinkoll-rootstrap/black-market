package com.rootstrap.android.ui.custom.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rootstrap.android.ui.compose_navigation.NavigationRoutes

@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<NavigationRoutes>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation {
        items.forEach { item ->
            item.icon?.let { icon ->
                BottomNavigationItem(
                    item = item,
                    icon = icon,
                    currentRoute = currentRoute,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    item: NavigationRoutes,
    icon: Int,
    currentRoute: String?,
    navController: NavController
) {
    BottomNavigationItem(
        icon = {
            Icon(
                painterResource(id = icon),
                contentDescription = item.title
            )
        },
        label = { Text(text = item.title) },
        selectedContentColor = MaterialTheme.colors.onPrimary,
        unselectedContentColor = MaterialTheme.colors.onPrimary.copy(alpha = ContentAlpha.medium),
        alwaysShowLabel = true,
        selected = currentRoute?.contains(item.route) ?: false,
        onClick = {
            navController.navigate(item.route) {

                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}
