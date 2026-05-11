package com.ciromine.example.producttest.data.mapper

import com.ciromine.example.producttest.data.local.entities.ProductEntity
import com.ciromine.example.producttest.data.local.entities.RatingEntity
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
            )
        )

        val domainProductList = with(mapper) {
            remoteProductList.toDomain()
        }

        assertEquals(1, domainProductList.results.size)
        assertEquals(1, domainProductList.results[0].id)
        assertEquals("Mochila Test", domainProductList.results[0].title)
        assertEquals(109.95, domainProductList.results[0].price, 0.0)
        assertEquals("Descripción de la mochila", domainProductList.results[0].description)
        assertEquals("men's clothing", domainProductList.results[0].category)
        assertEquals("url_imagen_1", domainProductList.results[0].image)
        assertEquals(4.5, domainProductList.results[0].rating.rate, 0.0)
        assertEquals(120, domainProductList.results[0].rating.count)
    }

    @Test
    fun `given List of ProductResponse when toEntityList(), then List of ProductEntity`() {
        // 1. Preparamos el mock de la API
        val remoteProductList = listOf(
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

        // 2. Ejecutamos el mapeo hacia Room
        val entityList = with(mapper) {
            remoteProductList.toEntityList()
        }

        // 3. Verificamos que se transformó correctamente a Entity
        assertEquals(1, entityList.size)
        assertEquals(2, entityList[0].id)
        assertEquals("Polera Test", entityList[0].title)
        assertEquals(22.3, entityList[0].price, 0.0)
        assertEquals("Descripción de la polera", entityList[0].description)
        assertEquals("men's clothing", entityList[0].category)
        assertEquals("url_imagen_2", entityList[0].image)
        // Verificamos el objeto anidado RatingEntity
        assertEquals(4.1, entityList[0].rating.rate, 0.0)
        assertEquals(259, entityList[0].rating.count)
    }

    @Test
    fun `given List of ProductEntity when toDomainList(), then DomainProductList`() {
        // 1. Preparamos el mock simulando lo que devolvería Room
        val localProductList = listOf(
            ProductEntity(
                id = 3,
                title = "Zapatillas Test",
                price = 89.90,
                description = "Descripción de las zapatillas",
                category = "shoes",
                image = "url_imagen_3",
                rating = RatingEntity(rate = 4.8, count = 300)
            )
        )

        // 2. Ejecutamos el mapeo hacia la Vista (Domain)
        val domainProductList = with(mapper) {
            localProductList.toDomainList()
        }

        // 3. Verificamos que se transformó correctamente a Domain
        assertEquals(1, domainProductList.results.size)
        assertEquals(3, domainProductList.results[0].id)
        assertEquals("Zapatillas Test", domainProductList.results[0].title)
        assertEquals(89.90, domainProductList.results[0].price, 0.0)
        assertEquals("Descripción de las zapatillas", domainProductList.results[0].description)
        assertEquals("shoes", domainProductList.results[0].category)
        assertEquals("url_imagen_3", domainProductList.results[0].image)
        // Verificamos el objeto anidado DomainRating
        assertEquals(4.8, domainProductList.results[0].rating.rate, 0.0)
        assertEquals(300, domainProductList.results[0].rating.count)
    }
}