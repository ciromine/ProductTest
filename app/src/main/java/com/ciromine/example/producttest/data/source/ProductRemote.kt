package com.ciromine.example.producttest.data.source

import com.ciromine.example.producttest.data.remote.model.ProductResponse

interface ProductRemote {

    suspend fun getProductList(): List<ProductResponse>
}