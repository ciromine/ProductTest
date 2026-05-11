package com.ciromine.example.producttest.data.remote

import com.ciromine.example.producttest.data.remote.model.ProductResponse
import com.ciromine.example.producttest.data.remote.model.RatingResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductRemoteImplTest {

    private val productApi = mockk<ProductApi>()
    private val remoteImpl = ProductRemoteImpl(productApi)

    @Test
    fun `when getProductList is called then returns list from API`() = runBlocking<Unit> {
        val mockResponse = listOf(
            ProductResponse(
                id = 1,
                title = "Producto Test",
                price = 99.9,
                description = "Descripción",
                category = "Categoría",
                image = "url",
                rating = RatingResponse(4.5, 100)
            )
        )

        coEvery { productApi.getProductList() } returns mockResponse
        val result = remoteImpl.getProductList()
        assertEquals(mockResponse, result)
        coVerify(exactly = 1) { productApi.getProductList() }
    }

    @Test(expected = RuntimeException::class)
    fun `given API throws exception when getProductList then propagates exception`() =
        runBlocking<Unit> {
            coEvery { productApi.getProductList() } throws RuntimeException("Error de red")
            remoteImpl.getProductList()
        }
}