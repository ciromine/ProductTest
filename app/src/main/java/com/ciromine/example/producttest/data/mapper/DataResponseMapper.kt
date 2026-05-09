package com.ciromine.example.producttest.data.mapper

import com.ciromine.example.producttest.data.remote.model.ProductResponse
import com.ciromine.example.producttest.data.remote.model.RatingResponse
import com.ciromine.example.producttest.domain.model.DomainProduct
import com.ciromine.example.producttest.domain.model.DomainProductList
import com.ciromine.example.producttest.domain.model.DomainRating
import javax.inject.Inject

class DataResponseMapper @Inject constructor() {

    fun List<ProductResponse>.toDomain() = DomainProductList(
        results = this.map { it.toDomainItem() }
    )

    private fun ProductResponse.toDomainItem() = DomainProduct(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        image = this.image,
        rating = this.rating.toDomainItem()
    )

    private fun RatingResponse.toDomainItem() = DomainRating(
        rate = this.rate,
        count = this.count
    )
}