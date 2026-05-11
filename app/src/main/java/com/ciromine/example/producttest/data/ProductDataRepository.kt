package com.ciromine.example.producttest.data

import android.util.Log
import com.ciromine.example.producttest.data.local.dao.ProductDao
import com.ciromine.example.producttest.data.mapper.DataResponseMapper
import com.ciromine.example.producttest.data.source.DataStoreManager
import com.ciromine.example.producttest.data.source.ProductRemote
import com.ciromine.example.producttest.domain.model.DomainProductList
import com.ciromine.example.producttest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductDataRepository @Inject constructor(
    private val remote: ProductRemote,
    private val productDao: ProductDao,
    private val dataStoreManager: DataStoreManager,
    private val mapper: DataResponseMapper
) :
    ProductRepository {

    private val TAG = ProductDataRepository::class.java.simpleName

    override fun getProductList(hasInternet: Boolean): Flow<DomainProductList> = flow {

        val localProducts = productDao.getAllProducts().first()

        if (hasInternet || localProducts.isEmpty()) {
            try {
                val remoteProducts = remote.getProductList()
                val entities = with(mapper) { remoteProducts.toEntityList() }
                productDao.insertAll(entities)
            } catch (e: HttpException) {
                if (localProducts.isEmpty()) {
                    throw e
                }
                Log.e(TAG, "Error HTTP: Usando caché local", e)

            } catch (e: IOException) {
                if (localProducts.isEmpty()) {
                    throw e
                }
                Log.e(TAG, "Error de red: Usando caché local", e)

            } catch (e: Exception) {
                if (localProducts.isEmpty()) {
                    throw e
                }
                Log.e(TAG, "Error desconocido: Usando caché local", e)
            }
        }
        val finalLocalProducts = productDao.getAllProducts().first()
        val domainList = with(mapper) { finalLocalProducts.toDomainList() }
        emit(domainList)
    }

    override suspend fun saveFavoriteProductId(productId: Int) {
        dataStoreManager.saveFavoriteProductId(productId)
    }

    override suspend fun removeFavoriteProductId(productId: Int) {
        dataStoreManager.removeFavoriteProductId(productId)
    }

    override fun isProductFavorite(productId: Int): Flow<Boolean> {
        return dataStoreManager.isProductFavorite(productId)
    }
}
