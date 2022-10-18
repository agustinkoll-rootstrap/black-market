package com.rootstrap.android.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rootstrap.android.ui.ui.theme.PaddingHalf
import com.rootstrap.android.ui.ui.theme.PaddingSixQuarters
import com.rootstrap.domain.Product

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
