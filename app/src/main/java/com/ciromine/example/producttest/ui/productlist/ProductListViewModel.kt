package com.ciromine.example.producttest.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciromine.example.producttest.domain.Resource
import com.ciromine.example.producttest.domain.usecases.GetProductListUseCase
import com.ciromine.example.producttest.domain.usecases.IsProductFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val isProductFavoriteUseCase: IsProductFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            getProductListUseCase().collect { result ->
                _uiState.value = when (result) {
                    is Resource.Loading -> ProductUiState.Loading
                    is Resource.Success -> ProductUiState.Success(result.data)
                    is Resource.Error -> ProductUiState.Error(result.message)
                }
            }
        }
    }

    fun isFavorite(productId: Int): Flow<Boolean> {
        return isProductFavoriteUseCase(productId)
    }
}