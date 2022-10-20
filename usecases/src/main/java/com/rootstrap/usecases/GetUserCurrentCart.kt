package com.rootstrap.usecases

import com.rootstrap.data.repository.UserCartRepository
import com.rootstrap.domain.CartItem

class GetUserCurrentCart(private val userCartRepository: UserCartRepository) {
    suspend operator fun invoke():List<CartItem>{
        return userCartRepository.getProductsInCart()
    }
}