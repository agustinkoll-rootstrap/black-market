package com.rootstrap.android.ui.dashboard

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.rootstrap.android.ui.ui.theme.PaddingHalf
import com.rootstrap.android.ui.ui.theme.PaddingSixQuarters
import com.rootstrap.domain.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel

@Composable
fun DashboardProductsList() {
    val context = LocalContext.current as ComponentActivity
    val dashboardViewModel: DashboardViewModel = context.getViewModel<DashboardViewModel>()
    val uiState: DashBoardUiState by dashboardViewModel.uiStateFlow.collectAsState()
    LaunchedEffect(key1 = true) {
        dashboardViewModel.load()
    }
    DashboardProductsList(
        uiState = uiState,
        addToFavourites = {
            dashboardViewModel.addToFavourites(it)
        },
        openDetail = {
            dashboardViewModel.openDetail(it)
        }
    )
}

@Composable
fun DashboardProductsList(
    uiState: DashBoardUiState,
    addToFavourites: (Product) -> Unit,
    openDetail: (Product) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = PaddingHalf)
            .padding(top = PaddingSixQuarters)
    ) {
        itemsIndexed(uiState.products) { _, product ->
            DashboardProductItem(
                modifier = Modifier,
                product = product,
                addToFavourites = addToFavourites,
                openDetail = openDetail
            )
        }
    }
}
