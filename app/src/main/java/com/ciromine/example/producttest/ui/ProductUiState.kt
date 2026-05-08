package com.ciromine.example.producttest.ui

import com.ciromine.example.producttest.domain.model.DomainProductList

sealed class ProductUiState {
    object Loading : ProductUiState()
    class Success(val productList: DomainProductList) : ProductUiState()
    class Error(val mensaje: String) : ProductUiState()
}