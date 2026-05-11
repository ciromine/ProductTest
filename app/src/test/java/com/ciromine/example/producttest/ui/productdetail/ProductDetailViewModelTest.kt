package com.ciromine.example.producttest.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import com.ciromine.example.producttest.domain.usecases.IsProductFavoriteUseCase
import com.ciromine.example.producttest.domain.usecases.RemoveFavoriteProductUseCase
import com.ciromine.example.producttest.domain.usecases.SaveFavoriteProductUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    private val isProductFavoriteUseCase = mockk<IsProductFavoriteUseCase>()
    private val saveFavoriteProductUseCase = mockk<SaveFavoriteProductUseCase>(relaxUnitFun = true)
    private val removeFavoriteProductUseCase = mockk<RemoveFavoriteProductUseCase>(relaxUnitFun = true)

    private lateinit var viewModel: ProductDetailViewModel

    private val testProductId = 5

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val savedStateHandle = SavedStateHandle(mapOf("productId" to testProductId))
        viewModel = ProductDetailViewModel(
            savedStateHandle = savedStateHandle,
            isProductFavoriteUseCase = isProductFavoriteUseCase,
            saveFavoriteProductUseCase = saveFavoriteProductUseCase,
            removeFavoriteProductUseCase = removeFavoriteProductUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when isFavorite then calls use case with productId from savedStateHandle`() = runTest {
        coEvery { isProductFavoriteUseCase(testProductId) } returns flowOf(true)
        val result = viewModel.isFavorite().first()

        assertEquals(true, result)
        coVerify(exactly = 1) { isProductFavoriteUseCase(testProductId) }
    }

    @Test
    fun `given currentIsFavorite is true when toggleFavorite then calls removeUseCase`() = runTest {
        viewModel.toggleFavorite(currentIsFavorite = true)

        coVerify(exactly = 1) { removeFavoriteProductUseCase(testProductId) }
        coVerify(exactly = 0) { saveFavoriteProductUseCase(any()) }
    }

    @Test
    fun `given currentIsFavorite is false when toggleFavorite then calls saveUseCase`() = runTest {
        viewModel.toggleFavorite(currentIsFavorite = false)

        coVerify(exactly = 1) { saveFavoriteProductUseCase(testProductId) }
        coVerify(exactly = 0) { removeFavoriteProductUseCase(any()) }
    }
}