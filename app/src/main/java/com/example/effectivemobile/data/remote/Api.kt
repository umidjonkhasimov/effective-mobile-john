package com.example.effectivemobile.data.remote

import com.example.effectivemobile.data.model.ApiResponse
import retrofit2.http.GET

interface Api {
    @GET("97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getProducts(): ApiResponse
}