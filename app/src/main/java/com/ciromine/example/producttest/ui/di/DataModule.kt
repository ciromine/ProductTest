package com.ciromine.example.producttest.ui.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.ciromine.example.producttest.data.ProductDataRepository
import com.ciromine.example.producttest.data.local.ProductDatabase
import com.ciromine.example.producttest.data.local.dao.ProductDao
import com.ciromine.example.producttest.data.remote.ProductRemoteImpl
import com.ciromine.example.producttest.data.source.ProductRemote
import com.ciromine.example.producttest.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideRepository(data: ProductDataRepository): ProductRepository {
        return data
    }

    @Singleton
    @Provides
    fun provideDataSource(dataSourceRemote: ProductRemoteImpl): ProductRemote {
        return dataSourceRemote
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "product_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: ProductDatabase): ProductDao {
        return database.productDao()
    }
}
