package com.example.effectivemobile.domain.repository

import com.example.effectivemobile.data.local.ProductDatabase
import com.example.effectivemobile.data.model.toDomain
import com.example.effectivemobile.data.remote.Api
import com.example.effectivemobile.domain.model.ProductDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val apiService: Api,
    productDatabase: ProductDatabase
) {
    private val dao = productDatabase.productDao()

    suspend fun getProducts(): List<ProductDomain> {
        val response = apiService.getProducts()
        return withContext(Dispatchers.IO) {
            response.items.map { it.toDomain() }
        }
    }

    suspend fun upsertItem(productDomain: ProductDomain) {
        return withContext(Dispatchers.IO) {
            dao.upsertItem(productDomain)
        }
    }

    suspend fun getAllFavorites(): Flow<List<ProductDomain>> {
        return withContext(Dispatchers.IO) {
            dao.getAllFavorites()
        }
    }
}