package com.ciromine.example.producttest.core

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineExecutionThread {

    fun uiThread(): CoroutineDispatcher

    fun ioThread(): CoroutineDispatcher
}