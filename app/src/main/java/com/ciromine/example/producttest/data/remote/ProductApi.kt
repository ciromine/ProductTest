package com.ciromine.example.producttest.data.remote

import com.ciromine.example.producttest.data.remote.model.ProductResponse
import com.ciromine.example.producttest.utils.Constants
import retrofit2.http.GET

interface ProductApi {

    @GET(Constants.PRODUCTS)
    suspend fun getProductList(): List<ProductResponse>
}