package com.rootstrap.data.repository

import com.rootstrap.domain.Product

class ProductRepository {
    suspend fun getProducts(): List<Product> {
        return listOf(
            Product(
                description = "",
                id = 1,
                name = "Chair",
                price = "40",
                imageUrl = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?%20%20%20%20%20%20%20%20ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80",
                isRestored = false
            ),
            Product(
                description = "",
                id = 1,
                name = "Chair 2",
                price = "50",
                imageUrl = "https://via.placeholder.com/300.png",
                isRestored = false
            )
        )
    }
}
