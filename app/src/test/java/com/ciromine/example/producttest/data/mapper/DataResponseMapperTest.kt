package com.ciromine.example.producttest.data.mapper

import com.ciromine.example.producttest.data.remote.model.ProductResponse
import com.ciromine.example.producttest.data.remote.model.RatingResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class DataResponseMapperTest {

    private val mapper = DataResponseMapper()

    @Test
    fun `given List of ProductResponse when toDomain(), then DomainProductList`() {
        val remoteProductList = listOf(
            ProductResponse(
                id = 1,
                title = "Mochila Test",
                price = 109.95,
                description = "Descripción de la mochila",
                category = "men's clothing",
                image = "url_imagen_1",
                rating = RatingResponse(rate = 4.5, count = 120)
            ),
            ProductResponse(
                id = 2,
                title = "Polera Test",
                price = 22.3,
                description = "Descripción de la polera",
                category = "men's clothing",
                image = "url_imagen_2",
                rating = RatingResponse(rate = 4.1, count = 259)
            )
        )

        val domainProductList = with(mapper) {
            remoteProductList.toDomain()
        }

        assertEquals(2, domainProductList.results.size)

        assertEquals(1, domainProductList.results[0].id)
        assertEquals("Mochila Test", domainProductList.results[0].title)
        assertEquals(109.95, domainProductList.results[0].price, 0.0)
        assertEquals("Descripción de la mochila", domainProductList.results[0].description)
        assertEquals("men's clothing", domainProductList.results[0].category)
        assertEquals("url_imagen_1", domainProductList.results[0].image)
        assertEquals(4.5, domainProductList.results[0].rating.rate, 0.0)
        assertEquals(120, domainProductList.results[0].rating.count)

        assertEquals(2, domainProductList.results[1].id)
        assertEquals("Polera Test", domainProductList.results[1].title)
        assertEquals(22.3, domainProductList.results[1].price, 0.0)
        assertEquals("Descripción de la polera", domainProductList.results[1].description)
        assertEquals("men's clothing", domainProductList.results[1].category)
        assertEquals("url_imagen_2", domainProductList.results[1].image)
        assertEquals(4.1, domainProductList.results[1].rating.rate, 0.0)
        assertEquals(259, domainProductList.results[1].rating.count)
    }
}