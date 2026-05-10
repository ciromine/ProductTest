package com.ciromine.example.producttest.domain.usecases

import com.ciromine.example.producttest.domain.repository.ProductRepository
import javax.inject.Inject

class RemoveFavoriteProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: Int) {
        repository.removeFavoriteProductId(productId = productId)
    }
}
