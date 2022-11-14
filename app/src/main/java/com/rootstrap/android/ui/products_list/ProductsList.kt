package com.rootstrap.android.ui.products_list

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.rootstrap.android.ui.SetContentOnSurface
import com.rootstrap.android.ui.ui.theme.PaddingNormal
import com.rootstrap.domain.Product
import org.koin.androidx.viewmodel.ext.android.getViewModel

@Composable
fun ProductsList() {
    val context = LocalContext.current as ComponentActivity
    val productListViewModel: ProductListViewModel = context.getViewModel<ProductListViewModel>()
    val uiState: ProductListUiState by productListViewModel.uiStateFlow.collectAsState()
    LaunchedEffect(key1 = true) {
        productListViewModel.load()
    }
    ProductsList(
        uiState = uiState,
        addToCartClick = { productListViewModel.addToCart(it) },
        onValueChanged = { productListViewModel.onValueChanged(it) },
        onClearTextClick = { productListViewModel.onClearTextClick() }
    )
}

@Composable
fun ProductsList(
    uiState: ProductListUiState,
    addToCartClick: (Product) -> Unit,
    onValueChanged: (String) -> Unit,
    onClearTextClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            value = uiState.searchValue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingNormal),
            onValueChanged = { onValueChanged(it) },
            onClearTextClick = { onClearTextClick() }
        )
        LazyColumn {
            itemsIndexed(uiState.products) { _, product ->
                ProductItem(
                    modifier = Modifier,
                    product = product,
                    addToCartClick = addToCartClick
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductListPreview() {
    val products = listOf(
        Product(
            description = "",
            id = 1,
            name = "Chair",
            price = "40",
            imageUrl = "",
            isRestored = false
        )
    )
    SetContentOnSurface(isDarkTheme = false) {
        ProductsList(ProductListUiState(isVisible = true, products), {}, {}) {}
    }
}
