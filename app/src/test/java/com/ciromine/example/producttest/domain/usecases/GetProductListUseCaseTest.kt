package com.ciromine.example.producttest.domain.usecases

import com.ciromine.example.producttest.domain.Resource
import com.ciromine.example.producttest.domain.model.DomainProduct
import com.ciromine.example.producttest.domain.model.DomainProductList
import com.ciromine.example.producttest.domain.model.DomainRating
import com.ciromine.example.producttest.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class GetProductListUseCaseTest {

    private val repository = mockk<ProductRepository>()
    private val getProductListUseCase = GetProductListUseCase(repository)

    private val stubDomainProductList = DomainProductList(
        results = listOf(
            DomainProduct(
                id = 1,
                title = "Mochila Test",
                price = 109.9,
                description = "Descripción de prueba",
                category = "Categoría",
                image = "url_imagen",
                rating = DomainRating(rate = 4.5, count = 120)
            )
        )
    )

    @Test
    fun `given repository returns success, then return Resource Success with product list`() =
        runBlocking {
            stubGetProductList(flow { emit(stubDomainProductList) })

            val result = getProductListUseCase().drop(1).first()

            val successData = (result as Resource.Success).data

            assertEquals(stubDomainProductList, successData)
        }

    @Test
    fun `given repository throws IOException, then return Resource Error with network message`() =
        runBlocking {
            stubGetProductList(flow { throw IOException() })
            val expectedErrorMessage = "Network Error"

            val result = getProductListUseCase().drop(1).first()

            val errorMessage = (result as Resource.Error).message

            assertEquals(expectedErrorMessage, errorMessage)
        }

    @Test
    fun `given repository throws HttpException, then return Resource Error with http message`() =
        runBlocking {
            stubGetProductList(flow { throw IOException() })
            val expectedErrorMessage = "Network Error"

            val result = getProductListUseCase().drop(1).first()

            val errorMessage = (result as Resource.Error).message

            assertEquals(expectedErrorMessage, errorMessage)
        }

    private fun stubGetProductList(domainProductListFlow: Flow<DomainProductList>) {
        coEvery { repository.getProductList() } returns domainProductListFlow
    }
}