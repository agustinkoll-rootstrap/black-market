package com.rootstrap.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.rootstrap.android.ui.compose_navigation.BlackMarketNavigationHost
import com.rootstrap.android.ui.compose_navigation.NavigationRoutes
import com.rootstrap.android.ui.custom.components.BottomNavigationBar
import com.rootstrap.android.ui.custom.components.TopBar
import com.rootstrap.android.ui.ui.theme.AndroidBaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavigationItems = listOf(
            NavigationRoutes.Dashboard,
            NavigationRoutes.ShoppingCart,
            NavigationRoutes.Favourite,
        )
        setContent {
            val navHostController = rememberNavController()
            SetContentOnSurface {
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navHostController,
                            items = bottomNavigationItems
                        )
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        BlackMarketNavigationHost(navHostController = navHostController)
                    }
                }
            }
        }
    }
}

@Composable
fun SetContentOnSurface(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    AndroidBaseTheme(isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
            content = content
        )
    }
}
