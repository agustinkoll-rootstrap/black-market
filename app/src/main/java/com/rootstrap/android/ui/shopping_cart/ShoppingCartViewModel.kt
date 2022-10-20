package com.rootstrap.android.ui.shopping_cart

import androidx.lifecycle.viewModelScope
import com.rootstrap.android.ui.base.BaseViewModel
import com.rootstrap.android.ui.base.UiState
import com.rootstrap.domain.Product
import com.rootstrap.usecases.GetProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingCartViewModel(private val getProducts: GetProducts) :
    BaseViewModel<ShoppingCartUiState>(ShoppingCartUiState()) {

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val products = getProducts().map { it.toShoppingCartItem() }
            setUiState {
                uiState.copy(
                    products = products,
                    total = calculateTotal(products),
                )
            }
        }
    }

    fun incrementQuantity(shoppingCartItem: ShoppingCartItem) {
        updateQuantity(shoppingCartItem, 1)
    }

    fun decrementQuantity(shoppingCartItem: ShoppingCartItem) {
        if (shoppingCartItem.quantity > 0)
            updateQuantity(shoppingCartItem, -1)
    }

    fun onRemoveItemTapped(shoppingCartItem: ShoppingCartItem) {
        val newList = uiState.products.toMutableList()
        newList.remove(shoppingCartItem)
        setUiState {
            uiState.copy(products = newList, total = calculateTotal(newList))
        }
    }

    private fun updateQuantity(shoppingCartItem: ShoppingCartItem, value: Int) {
        uiState.products.first { it == shoppingCartItem }.quantity += value
        setUiState {
            uiState.copy(
                total = calculateTotal(uiState.products)
            )
        }
    }

    fun onClearAllTapped() {
        setUiState {
            uiState.copy(
                products = emptyList(),
                total = 0
            )
        }
    }

    fun onGoToCheckOutTapped() {
    }

    private fun calculateTotal(products: List<ShoppingCartItem>): Int {
        var total = 0
        products.forEach {
            total += it.quantity * it.product.price.toInt()
        }
        return total
    }
}

data class ShoppingCartUiState(
    override val isVisible: Boolean = true,
    val products: List<ShoppingCartItem> = emptyList(),
    val total: Int = 0
) : UiState

data class ShoppingCartItem(
    val product: Product,
    var quantity: Int = 0
)

fun Product.toShoppingCartItem(): ShoppingCartItem =
    ShoppingCartItem(
        product = this,
        quantity = 1
    )
