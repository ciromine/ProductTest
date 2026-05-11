package com.ciromine.example.producttest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ciromine.example.producttest.data.local.dao.ProductDao
import com.ciromine.example.producttest.data.local.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}