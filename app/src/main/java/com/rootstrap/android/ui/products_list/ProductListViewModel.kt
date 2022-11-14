package com.rootstrap.android.ui.products_list

import androidx.lifecycle.viewModelScope
import com.rootstrap.android.ui.base.BaseViewModel
import com.rootstrap.android.ui.base.UiState
import com.rootstrap.domain.Product
import com.rootstrap.usecases.GetProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(private val getProducts: GetProducts) :
    BaseViewModel<ProductListUiState>(ProductListUiState()) {

    var productsCopy = emptyList<Product>()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            productsCopy = getProducts()
            setUiState {
                uiState.copy(products = productsCopy)
            }
        }
    }

    fun onValueChanged(value: String) {
        val list = productsCopy.filter { it.name.lowercase().contains(value) }
        setUiState {
            uiState.copy(products = list, searchValue = value)
        }
    }

    fun onClearTextClick() {
        setUiState {
            uiState.copy(products = productsCopy, searchValue = "")
        }
    }

    fun addToCart(product: Product) {
    }
}

data class ProductListUiState(
    override val isVisible: Boolean = true,
    val products: List<Product> = emptyList(),
    val searchValue: String = ""
) : UiState
