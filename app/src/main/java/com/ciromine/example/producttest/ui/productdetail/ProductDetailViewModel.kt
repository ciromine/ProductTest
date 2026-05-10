package com.ciromine.example.producttest.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciromine.example.producttest.domain.usecases.IsProductFavoriteUseCase
import com.ciromine.example.producttest.domain.usecases.RemoveFavoriteProductUseCase
import com.ciromine.example.producttest.domain.usecases.SaveFavoriteProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val isProductFavoriteUseCase: IsProductFavoriteUseCase,
    private val saveFavoriteProductUseCase: SaveFavoriteProductUseCase,
    private val removeFavoriteProductUseCase: RemoveFavoriteProductUseCase
) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle["productId"])

    fun isFavorite(): Flow<Boolean> {
        return isProductFavoriteUseCase(productId)
    }

    fun toggleFavorite(currentIsFavorite: Boolean) {
        viewModelScope.launch {
            if (currentIsFavorite) {
                removeFavoriteProductUseCase(productId)
            } else {
                saveFavoriteProductUseCase(productId)
            }
        }
    }
}