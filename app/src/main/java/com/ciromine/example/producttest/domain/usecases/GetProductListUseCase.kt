package com.ciromine.example.producttest.domain.usecases

import com.ciromine.example.producttest.domain.repository.ProductRepository
import com.ciromine.example.producttest.ui.ProductUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<ProductUiState> = flow {
        try {
            //emit(ProductUiState.Loading)

            val productList = repository.getProductList().first()

            emit(ProductUiState.Success(productList))

        } catch (e: HttpException) {
            emit(ProductUiState.Error(e.localizedMessage ?: "An unexpected HTTP error occurred"))
        } catch (e: IOException) {
            emit(ProductUiState.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}