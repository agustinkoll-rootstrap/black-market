package com.rootstrap.android.ui.dashboard

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.rootstrap.android.R
import com.rootstrap.android.ui.SetContentOnSurface
import com.rootstrap.android.ui.compose_navigation.NavigationCallbacks.navigateToProductsList
import com.rootstrap.android.ui.compose_navigation.NavigationCallbacks.openProductDetail
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
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            DashboardProductsList(
                uiState = uiState,
                addToFavourites = {
                    dashboardViewModel.addToFavourites(it)
                },
                openDetail = {
                    openProductDetail(navController, it)
                }
            )

            SeeAllButton {
                navigateToProductsList(navController)
            }

            DashboardSaleBanner()

            DashboardPaymentsMethodsBanner()

            DashboardBannerShipment()
        }
    }
}

@Composable
fun ColumnScope.SeeAllButton(onSeeAllButtonClick: () -> Unit) {
    Text(
        text = stringResource(R.string.txt_see_all),
        modifier = Modifier
            .padding(top = PaddingNormal)
            .align(Alignment.CenterHorizontally)
            .clickable { onSeeAllButtonClick() },
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.secondaryVariant,
    )
}
