package com.ciromine.example.producttest.data

import com.ciromine.example.producttest.data.local.dao.ProductDao
import com.ciromine.example.producttest.data.local.entities.ProductEntity
import com.ciromine.example.producttest.data.local.entities.RatingEntity
import com.ciromine.example.producttest.data.mapper.DataResponseMapper
import com.ciromine.example.producttest.data.remote.model.ProductResponse
import com.ciromine.example.producttest.data.remote.model.RatingResponse
import com.ciromine.example.producttest.data.source.DataStoreManager
import com.ciromine.example.producttest.data.source.ProductRemote
import com.ciromine.example.producttest.domain.model.DomainProduct
import com.ciromine.example.producttest.domain.model.DomainProductList
import com.ciromine.example.producttest.domain.model.DomainRating
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductDataRepositoryTest {

    private val remote = mockk<ProductRemote>()
    private val productDao = mockk<ProductDao>(relaxUnitFun = true)
    private val dataStoreManager = mockk<DataStoreManager>(relaxUnitFun = true)
    private val mapper = mockk<DataResponseMapper>()

    private val repository = ProductDataRepository(remote, productDao, dataStoreManager, mapper)

    private val remoteProductList = listOf(
        ProductResponse(
            id = 1,
            title = "Mochila",
            price = 10.0,
            description = "A",
            category = "B",
            image = "C",
            rating = RatingResponse(4.0, 10)
        )
    )
    private val entityProductList = listOf(
        ProductEntity(
            id = 1,
            title = "Mochila",
            price = 10.0,
            description = "A",
            category = "B",
            image = "C",
            rating = RatingEntity(4.0, 10)
        )
    )
    private val domainProductList = DomainProductList(
        results = listOf(
            DomainProduct(
                id = 1,
                title = "Mochila",
                price = 10.0,
                description = "A",
                category = "B",
                image = "C",
                rating = DomainRating(4.0, 10)
            )
        )
    )

    @Test
    fun `given hasInternet is true when getProductList then fetches remote, saves to dao, and emits domain list`(): Unit = runBlocking {
            coEvery { productDao.getAllProducts() } returnsMany listOf(
                flowOf(emptyList()),
                flowOf(entityProductList)
            )

            stubGetRemoteProductList(remoteProductList)
            stubMapToEntityList(remoteProductList, entityProductList)
            stubMapToDomainList(entityProductList, domainProductList)

            repository.getProductList(hasInternet = true).collect { result ->
                assertEquals(domainProductList, result)
            }

            coVerify(exactly = 2) { productDao.getAllProducts() }
            coVerify(exactly = 1) { remote.getProductList() }
            coVerify(exactly = 1) { productDao.insertAll(entityProductList) }
            every { with(mapper) { remoteProductList.toEntityList() } }
            every { with(mapper) { entityProductList.toDomainList() } }
        }

    @Test
    fun `given hasInternet is false and cache has data when getProductList then emits domain list from dao without calling remote`(): Unit = runBlocking {
            coEvery { productDao.getAllProducts() } returns flowOf(entityProductList)
            stubMapToDomainList(entityProductList, domainProductList)

            repository.getProductList(hasInternet = false).collect { result ->
                assertEquals(domainProductList, result)
            }

            coVerify(exactly = 2) { productDao.getAllProducts() }
            coVerify(exactly = 0) { remote.getProductList() }
            coVerify(exactly = 0) { productDao.insertAll(any()) }
            every { with(mapper) { entityProductList.toDomainList() } }
        }

    @Test
    fun `when saveFavoriteProductId then calls DataStoreManager`() = runBlocking {
        repository.saveFavoriteProductId(5)
        coVerify(exactly = 1) { dataStoreManager.saveFavoriteProductId(5) }
    }

    @Test
    fun `when removeFavoriteProductId then calls DataStoreManager`() = runBlocking {
        repository.removeFavoriteProductId(10)
        coVerify(exactly = 1) { dataStoreManager.removeFavoriteProductId(10) }
    }

    @Test
    fun `when isProductFavorite then emits result from DataStoreManager`() = runBlocking<Unit> {
        every { dataStoreManager.isProductFavorite(3) } returns flowOf(true)

        val result = repository.isProductFavorite(3).first()

        assertEquals(true, result)
        verify(exactly = 1) { dataStoreManager.isProductFavorite(3) }
    }

    private fun stubGetRemoteProductList(response: List<ProductResponse>) {
        coEvery { remote.getProductList() } returns response
    }

    private fun stubMapToEntityList(remote: List<ProductResponse>, entity: List<ProductEntity>) {
        every { with(mapper) { remote.toEntityList() } } returns entity
    }

    private fun stubMapToDomainList(entity: List<ProductEntity>, domain: DomainProductList) {
        every { with(mapper) { entity.toDomainList() } } returns domain
    }
}