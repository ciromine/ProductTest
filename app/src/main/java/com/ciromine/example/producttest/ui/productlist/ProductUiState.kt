package com.ciromine.example.producttest.ui.productlist

import com.ciromine.example.producttest.domain.model.DomainProductList

sealed class ProductUiState {
    object Loading : ProductUiState()
    class Success(val productList: DomainProductList, val isOffline: Boolean = false) : ProductUiState()
    class Error(val mensaje: String) : ProductUiState()
}