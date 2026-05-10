package com.ciromine.example.producttest.domain.usecases

import com.ciromine.example.producttest.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveFavoriteProductUseCaseTest {

    private val repository = mockk<ProductRepository>()
    private val saveFavoriteProductUseCase = SaveFavoriteProductUseCase(repository)

    @Test
    fun `when invoke is called with a productId, then calls the repository to save the favorite status`() =
        runBlocking {
            val productIdToSave = 12

            coEvery { repository.saveFavoriteProductId(productIdToSave) } coAnswers { }

            saveFavoriteProductUseCase(productIdToSave)

            coVerify(exactly = 1) { repository.saveFavoriteProductId(productIdToSave) }
        }

    @Test
    fun `when invoke is called with a different productId, then calls the repository with that specific id`() =
        runBlocking {
            val anotherProductIdToSave = 45

            coEvery { repository.saveFavoriteProductId(anotherProductIdToSave) } coAnswers { }

            saveFavoriteProductUseCase(anotherProductIdToSave)

            coVerify(exactly = 1) { repository.saveFavoriteProductId(anotherProductIdToSave) }
        }
}