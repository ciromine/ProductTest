package com.ciromine.example.producttest.domain.usecases

import com.ciromine.example.producttest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsProductFavoriteUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(productId: Int): Flow<Boolean> {
        return repository.isProductFavorite(productId = productId)
    }
}
