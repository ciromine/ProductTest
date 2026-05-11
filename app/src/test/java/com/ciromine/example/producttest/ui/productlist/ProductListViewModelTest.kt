package com.ciromine.example.producttest.ui.productlist

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.ciromine.example.producttest.domain.Resource
import com.ciromine.example.producttest.domain.model.DomainProductList
import com.ciromine.example.producttest.domain.usecases.GetProductListUseCase
import com.ciromine.example.producttest.domain.usecases.IsProductFavoriteUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    private val getProductListUseCase = mockk<GetProductListUseCase>()
    private val isProductFavoriteUseCase = mockk<IsProductFavoriteUseCase>()

    private val context = mockk<Context>()
    private val connectivityManager = mockk<ConnectivityManager>()
    private val network = mockk<Network>()
    private val networkCapabilities = mockk<NetworkCapabilities>()

    private val mockDomainList = mockk<DomainProductList>()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given network available when ViewModel is created then uiState is Success and isOffline is false`(): Unit = runTest {
        stubNetworkState(hasInternet = true)
        every { getProductListUseCase(hasInternet = true) } returns flowOf(Resource.Success(mockDomainList))

        val viewModel = ProductListViewModel(context, getProductListUseCase, isProductFavoriteUseCase)
        val currentState = viewModel.uiState.value
        assertTrue(currentState is ProductUiState.Success)

        val successState = currentState as ProductUiState.Success
        assertEquals(mockDomainList, successState.productList)
        assertFalse(successState.isOffline)
    }

    @Test
    fun `given network unavailable when ViewModel is created then uiState is Success and isOffline is true`(): Unit = runTest {
        stubNetworkState(hasInternet = false)
        every { getProductListUseCase(hasInternet = false) } returns flowOf(Resource.Success(mockDomainList))

        val viewModel = ProductListViewModel(context, getProductListUseCase, isProductFavoriteUseCase)

        val currentState = viewModel.uiState.value
        assertTrue(currentState is ProductUiState.Success)

        val successState = currentState as ProductUiState.Success
        assertEquals(mockDomainList, successState.productList)
        assertTrue(successState.isOffline)
    }

    @Test
    fun `when ViewModel is created and UseCase returns Error then uiState is Error`(): Unit = runTest {
        stubNetworkState(hasInternet = true)
        val errorMessage = "Falla de servidor"
        every { getProductListUseCase(hasInternet = true) } returns flowOf(Resource.Error(errorMessage))

        val viewModel = ProductListViewModel(context, getProductListUseCase, isProductFavoriteUseCase)

        val currentState = viewModel.uiState.value
        assertTrue(currentState is ProductUiState.Error)
        assertEquals(errorMessage, (currentState as ProductUiState.Error).mensaje)
    }

    @Test
    fun `when isFavorite is called then returns flow from use case`(): Unit = runTest {
        stubNetworkState(hasInternet = true)
        every { getProductListUseCase(any()) } returns flowOf(Resource.Loading())
        val viewModel = ProductListViewModel(context, getProductListUseCase, isProductFavoriteUseCase)

        val testProductId = 5
        every { isProductFavoriteUseCase(testProductId) } returns flowOf(true)
        val result = viewModel.isFavorite(testProductId).first()

        assertTrue(result)
        verify(exactly = 1) { isProductFavoriteUseCase(testProductId) }
    }

    private fun stubNetworkState(hasInternet: Boolean) {
        if (hasInternet) {
            every { connectivityManager.activeNetwork } returns network
            every { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities
            every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true
        } else {
            every { connectivityManager.activeNetwork } returns null
        }
    }
}