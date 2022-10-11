package com.rootstrap.android.ui.products_list

import androidx.lifecycle.viewModelScope
import com.rootstrap.android.ui.base.BaseViewModel
import com.rootstrap.android.ui.base.UiState
import com.rootstrap.domain.Product
import com.rootstrap.usecases.GetProducts
import kotlinx.coroutines.launch

class ProductListViewModel(private val getProducts: GetProducts) :
    BaseViewModel<ProductListUiState>(ProductListUiState()) {
    fun load() {
        viewModelScope.launch {
            val products = getProducts()
            setUiState {
                uiState.copy(products = products)
            }
        }
    }

    fun addToCart(product: Product) {
    }
}

data class ProductListUiState(
    override val isVisible: Boolean = true,
    val products: List<Product> = emptyList()
) : UiState