package com.ciromine.example.producttest.domain.usecases

import com.ciromine.example.producttest.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class IsProductFavoriteUseCaseTest {

    private val repository = mockk<ProductRepository>()
    private val isProductFavoriteUseCase = IsProductFavoriteUseCase(repository)

    @Test
    fun `given repository returns true, then return true`() = runBlocking {
        stubIsProductFavorite(flow { emit(true) })

        val productId = 1

        val result = isProductFavoriteUseCase(productId).first()

        assertEquals(true, result)
    }

    @Test
    fun `given repository returns false, then return false`() = runBlocking {
        stubIsProductFavorite(flow { emit(false) })

        val productId = 1

        val result = isProductFavoriteUseCase(productId).first()

        assertEquals(false, result)
    }

    private fun stubIsProductFavorite(isFavoriteFlow: Flow<Boolean>) {
        coEvery { repository.isProductFavorite(any()) } returns isFavoriteFlow
    }
}