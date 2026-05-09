package com.ciromine.example.producttest.data.remote

import com.ciromine.example.producttest.data.remote.model.ProductResponse
import com.ciromine.example.producttest.data.source.ProductRemote
import javax.inject.Inject

class ProductRemoteImpl @Inject constructor(
    private val productApi: ProductApi
) :
    ProductRemote {

    override suspend fun getProductList(): List<ProductResponse> =
        productApi.getProductList()
}