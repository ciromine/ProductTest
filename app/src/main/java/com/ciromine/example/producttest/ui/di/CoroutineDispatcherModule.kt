package com.ciromine.example.producttest.ui.di

import com.ciromine.example.core.CoroutineExecutionThread
import com.ciromine.example.core.ExecutionThread
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CoroutineDispatcherModule {

    @Reusable
    @Provides
    fun provideCoroutineDispatchers(): CoroutineExecutionThread {
        return ExecutionThread()
    }
}