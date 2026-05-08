package com.ciromine.example.producttest.domain.usecases

import com.ciromine.example.producttest.domain.Resource
import com.ciromine.example.producttest.domain.model.DomainProductList
import com.ciromine.example.producttest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<DomainProductList>> = flow {
        try {
            emit(Resource.Loading())
            val productList = repository.getProductList().first()
            emit(Resource.Success(productList))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Network Error"))
        }
    }
}