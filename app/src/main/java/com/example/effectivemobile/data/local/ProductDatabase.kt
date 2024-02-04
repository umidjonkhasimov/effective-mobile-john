package com.example.effectivemobile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.effectivemobile.domain.model.ProductDomain

@Database(
    entities = [ProductDomain::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ProductTypeConverters.FeedbackTypeConverter::class,
    ProductTypeConverters.InfoTypeConverter::class,
    ProductTypeConverters.PriceTypeConverter::class,
    ProductTypeConverters.TagsTypeConverter::class,
    ProductTypeConverters.ImagesTypeConverter::class
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): Dao
}