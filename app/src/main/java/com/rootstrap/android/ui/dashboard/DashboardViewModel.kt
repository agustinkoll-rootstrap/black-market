package com.rootstrap.android.ui.dashboard

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.rootstrap.android.ui.base.BaseViewModel
import com.rootstrap.android.ui.base.UiState
import com.rootstrap.domain.Product
import com.rootstrap.usecases.GetProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel(private val getProducts: GetProducts) : BaseViewModel<DashBoardUiState>(
    DashBoardUiState(true, emptyList())
) {
    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val products = getProducts()
            setUiState {
                uiState.copy(products = products)
            }
        }
    }

    fun addToFavourites(product: Product) {
        Log.d("productList", product.name)
    }

    fun openDetail(product: Product) {
        Log.d("productList", product.name)
    }
}

data class DashBoardUiState(
    override val isVisible: Boolean,
    val products: List<Product>,
) : UiState
