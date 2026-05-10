package com.ciromine.example.producttest.domain.usecases

import com.ciromine.example.producttest.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemoveFavoriteProductUseCaseTest {

    private val repository = mockk<ProductRepository>()
    private val removeFavoriteProductUseCase = RemoveFavoriteProductUseCase(repository)

    @Test
    fun `when invoke is called with a productId, then calls the repository to remove the favorite status`() =
        runBlocking {
            val productIdToRemove = 7

            coEvery { repository.removeFavoriteProductId(productIdToRemove) } coAnswers { }

            removeFavoriteProductUseCase(productIdToRemove)

            coVerify(exactly = 1) { repository.removeFavoriteProductId(productIdToRemove) }
        }

    @Test
    fun `when invoke is called with a different productId, then calls the repository with that specific id`() =
        runBlocking {
            val anotherProductIdToRemove = 23

            coEvery { repository.removeFavoriteProductId(anotherProductIdToRemove) } coAnswers { }

            removeFavoriteProductUseCase(anotherProductIdToRemove)

            coVerify(exactly = 1) { repository.removeFavoriteProductId(anotherProductIdToRemove) }
        }
}