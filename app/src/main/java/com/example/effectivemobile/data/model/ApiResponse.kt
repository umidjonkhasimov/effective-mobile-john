package com.example.effectivemobile.data.model

import com.example.effectivemobile.domain.model.ApiDataDomain

data class ApiResponse(
    val items: List<ProductResponse>
)

fun ApiResponse.toDomain(): ApiDataDomain {
    return ApiDataDomain(
        items = items.map { it.toDomain() }
    )
}