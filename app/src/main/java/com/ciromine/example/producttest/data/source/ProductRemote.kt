package com.ciromine.example.producttest.data.source

import com.ciromine.example.producttest.data.remote.model.ProductListResponse

interface ProductRemote {

    suspend fun getProductList(): ProductListResponse
}