package com.ciromine.example.producttest.domain.repository

import com.ciromine.example.producttest.domain.model.DomainProductList
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductList(): Flow<DomainProductList>
    suspend fun saveFavoriteProductId(productId: Int)
    suspend fun removeFavoriteProductId(productId: Int)
    fun isProductFavorite(productId: Int): Flow<Boolean>
}