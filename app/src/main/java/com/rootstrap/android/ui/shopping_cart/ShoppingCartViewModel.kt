package com.rootstrap.android.ui.shopping_cart

import androidx.lifecycle.viewModelScope
import com.rootstrap.android.ui.base.BaseViewModel
import com.rootstrap.android.ui.base.UiState
import com.rootstrap.domain.CartItem
import com.rootstrap.usecases.GetUserCurrentCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingCartViewModel(private val getUserCurrentCart: GetUserCurrentCart) :
    BaseViewModel<ShoppingCartUiState>(ShoppingCartUiState()) {

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val products = getUserCurrentCart().map { it.toShoppingCartItem() }
            setUiState {
                uiState.copy(
                    products = products,
                    total = calculateTotal(products),
                )
            }
        }
    }

    fun incrementQuantity(shoppingCartItemModel: ShoppingCartItemModel) {
        updateQuantity(shoppingCartItemModel, 1)
    }

    fun decrementQuantity(shoppingCartItemModel: ShoppingCartItemModel) {
        if (shoppingCartItemModel.quantity > 0)
            updateQuantity(shoppingCartItemModel, -1)
    }

    fun onRemoveItemTapped(shoppingCartItemModel: ShoppingCartItemModel) {
        val newList = uiState.products.toMutableList()
        newList.remove(shoppingCartItemModel)
        setUiState {
            uiState.copy(products = newList, total = calculateTotal(newList))
        }
    }

    private fun updateQuantity(shoppingCartItemModel: ShoppingCartItemModel, value: Int) {
        uiState.products.first { it == shoppingCartItemModel }.quantity += value
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

    private fun calculateTotal(products: List<ShoppingCartItemModel>): Int {
        var total = 0
        products.forEach {
            total += it.quantity * it.price.toInt()
        }
        return total
    }
}

data class ShoppingCartUiState(
    override val isVisible: Boolean = true,
    val products: List<ShoppingCartItemModel> = emptyList(),
    val total: Int = 0
) : UiState

data class ShoppingCartItemModel(
    val description: String,
    val id: Int,
    val name: String,
    val price: String,
    val imageUrl: String,
    val isRestored: Boolean = false,
    var quantity: Int = 0
)

fun CartItem.toShoppingCartItem(): ShoppingCartItemModel =
    ShoppingCartItemModel(
        description = description,
        quantity = 1,
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        isRestored = isRestored,
    )
