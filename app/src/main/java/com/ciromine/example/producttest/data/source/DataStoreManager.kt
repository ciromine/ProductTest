package com.ciromine.example.producttest.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val favoriteProductKey = stringPreferencesKey(FAVORITE_PRODUCTS)

    suspend fun saveFavoriteProductId(productId: Int) {
        dataStore.edit { preferences ->
            val currentFavorites =
                preferences[favoriteProductKey]?.split(",")?.mapNotNull { it.toIntOrNull() }
                    ?.toMutableList() ?: mutableListOf()
            if (!currentFavorites.contains(productId)) {
                currentFavorites.add(productId)
                preferences[favoriteProductKey] = currentFavorites.joinToString(",")
            }
        }
    }

    suspend fun removeFavoriteProductId(productId: Int) {
        dataStore.edit { preferences ->
            val currentFavorites =
                preferences[favoriteProductKey]?.split(",")?.mapNotNull { it.toIntOrNull() }
                    ?.toMutableList() ?: mutableListOf()
            currentFavorites.remove(productId)
            preferences[favoriteProductKey] = currentFavorites.joinToString(",")
        }
    }

    fun isProductFavorite(productId: Int): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[favoriteProductKey]?.split(",")?.mapNotNull { it.toIntOrNull() }
                ?.contains(productId) ?: false
        }
    }
}

private const val FAVORITE_PRODUCTS = "favorite_products"
