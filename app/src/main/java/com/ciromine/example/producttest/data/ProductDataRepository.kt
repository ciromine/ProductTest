package com.ciromine.example.producttest.data

import com.ciromine.example.producttest.data.mapper.DataResponseMapper
import com.ciromine.example.producttest.data.source.DataStoreManager
import com.ciromine.example.producttest.data.source.ProductRemote
import com.ciromine.example.producttest.domain.model.DomainProductList
import com.ciromine.example.producttest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductDataRepository @Inject constructor(
    private val remote: ProductRemote,
    private val dataStoreManager: DataStoreManager,
    private val mapper: DataResponseMapper
) :
    ProductRepository {

    override fun getProductList(): Flow<DomainProductList> = flow {
        val productList = with(mapper) {
            remote.getProductList().toDomain()
        }
        emit(productList)
    }

    override suspend fun saveFavoriteProductId(productId: Int) {
        dataStoreManager.saveFavoriteProductId(productId)
    }

    override suspend fun removeFavoriteProductId(productId: Int) {
        dataStoreManager.removeFavoriteProductId(productId)
    }

    override fun isProductFavorite(productId: Int): Flow<Boolean> = flow {
        emit(dataStoreManager.isProductFavorite(productId))
    }
}