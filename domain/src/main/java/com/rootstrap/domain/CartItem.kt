package com.rootstrap.domain

data class CartItem(
    val description: String,
    val id: Int,
    val name: String,
    val price: String,
    val imageUrl: String,
    val isRestored: Boolean = false,
    val quantity:Int,
)
