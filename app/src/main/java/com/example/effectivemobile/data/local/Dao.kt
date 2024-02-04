package com.example.effectivemobile.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.effectivemobile.domain.model.ProductDomain
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM productdomain")
    fun getAllProducts(): Flow<List<ProductDomain>>

    @Insert
    fun insertAllProducts(products: List<ProductDomain>)

    @Upsert
    fun upsertItem(productDomain: ProductDomain)

    @Query("SELECT * FROM productdomain WHERE isFavorite")
    fun getAllFavorites(): Flow<List<ProductDomain>>

    @Query("DELETE FROM productdomain")
    fun clearTable()

    @Query("SELECT COUNT(*) FROM productdomain WHERE isFavorite = 1")
    fun getRowCount(): Flow<Int>
}