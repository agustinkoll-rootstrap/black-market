package com.rootstrap.data.repository

import com.rootstrap.domain.CartItem
import com.rootstrap.domain.Product

class UserCartRepository {
    suspend fun getProductsInCart(): List<CartItem> {
        return listOf(
            CartItem(
                description = "",
                id = 1,
                name = "Chair",
                price = "40",
                imageUrl = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?%20%20%20%20%20%20%20%20ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80",
                isRestored = false,
                quantity = 1,
            ),
            CartItem(
                description = "",
                id = 2,
                name = "Chair 2",
                price = "50",
                imageUrl = "https://via.placeholder.com/300.png",
                isRestored = true,
                quantity = 1,
            ),
            CartItem(
                description = "",
                id = 3,
                name = "Chair 3",
                price = "45",
                imageUrl = "https://via.placeholder.com/300.png",
                isRestored = true,
                quantity = 1,
            ),
            CartItem(
                description = "",
                id = 4,
                name = "Chair 4",
                price = "60",
                imageUrl = "https://via.placeholder.com/300.png",
                isRestored = true,
                quantity = 1,
            ),
        )
    }
}
