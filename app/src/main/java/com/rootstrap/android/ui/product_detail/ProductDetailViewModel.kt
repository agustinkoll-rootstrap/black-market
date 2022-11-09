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
                imageUrl = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?%20%20%20%20%20%20%20%20ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80",
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
