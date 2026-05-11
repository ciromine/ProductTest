package com.ciromine.example.producttest.ui.productlist

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciromine.example.producttest.domain.Resource
import com.ciromine.example.producttest.domain.usecases.GetProductListUseCase
import com.ciromine.example.producttest.domain.usecases.IsProductFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getProductListUseCase: GetProductListUseCase,
    private val isProductFavoriteUseCase: IsProductFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        val hasInternet = isNetworkAvailable()
        viewModelScope.launch {
            getProductListUseCase(hasInternet = hasInternet).collect { result ->
                _uiState.value = when (result) {
                    is Resource.Loading -> ProductUiState.Loading
                    is Resource.Success -> ProductUiState.Success(result.data, isOffline = !hasInternet)
                    is Resource.Error -> ProductUiState.Error(result.message)
                }
            }
        }
    }

    fun isFavorite(productId: Int): Flow<Boolean> {
        return isProductFavoriteUseCase(productId)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}
