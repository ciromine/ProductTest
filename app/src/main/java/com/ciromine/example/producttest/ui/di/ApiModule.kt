package com.ciromine.example.producttest.ui.di

import com.ciromine.example.producttest.data.remote.ProductApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Reusable
    @Provides
    fun providePokeApiService(@Named("pokeApiRetrofit") retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }
}