package com.rootstrap.usecases

import com.rootstrap.data.repository.ProductRepository
import com.rootstrap.domain.Product

class GetProducts(private val productRepository: ProductRepository) {
    suspend operator fun invoke():List<Product>{
        return productRepository.getProducts()
    }
}