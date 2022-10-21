package com.rootstrap.android.ui.product_detail

import androidx.lifecycle.viewModelScope
import com.rootstrap.android.ui.base.BaseViewModel
import com.rootstrap.android.ui.base.UiState
import com.rootstrap.domain.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailViewModel() :
    BaseViewModel<ProductDetailUiState>(ProductDetailUiState()) {

    fun load(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = Product(
                description = "",
                id = 2,
                name = "Chair 2",
                price = "50",
                imageUrl = "https://via.placeholder.com/300.png",
                isRestored = true
            )
            setUiState {
                uiState.copy(product = product)
            }
        }
    }

    fun addToCart(product: Product) {
    }
}

data class ProductDetailUiState(
    override val isVisible: Boolean = true,
    val product: Product? = null
) : UiState
