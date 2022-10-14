package com.rootstrap.android.ui.dashboard

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.rootstrap.android.ui.SetContentOnSurface
import com.rootstrap.android.ui.compose_navigation.NavigationCallbacks.navigateToProductsList
import com.rootstrap.android.ui.ui.theme.BlueLink
import com.rootstrap.android.ui.ui.theme.PaddingNormal
import org.koin.androidx.viewmodel.ext.android.getViewModel

@Composable
fun Dashboard(navController: NavHostController) {
    val context = LocalContext.current as ComponentActivity
    val dashboardViewModel: DashboardViewModel = context.getViewModel<DashboardViewModel>()
    val uiState: DashBoardUiState by dashboardViewModel.uiStateFlow.collectAsState()

    LaunchedEffect(key1 = true) {
        dashboardViewModel.load()
    }

    SetContentOnSurface {
        Column {
            DashboardProductsList(
                uiState = uiState,
                addToFavourites = {
                    dashboardViewModel.addToFavourites(it)
                },
                openDetail = {
                    dashboardViewModel.openDetail(it)
                }
            )

            SeeAllButton {
                navigateToProductsList(navController)
            }
        }
    }
}

@Composable
fun ColumnScope.SeeAllButton(onSeeAllButtonClick: () -> Unit) {
    Text(
        text = "See all",
        modifier = Modifier
            .padding(top = PaddingNormal)
            .align(Alignment.CenterHorizontally)
            .clickable { onSeeAllButtonClick() },
        style = MaterialTheme.typography.body1,
        color = BlueLink,
    )
}
