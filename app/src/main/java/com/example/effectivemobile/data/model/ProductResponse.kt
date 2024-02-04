package com.example.effectivemobile.data.model

import com.example.effectivemobile.domain.model.ProductDomain
import com.example.effectivemobile.util.getImagesByProductId

data class ProductResponse(
    val available: Int,
    val description: String,
    val feedback: FeedbackResponse,
    val id: String,
    val info: List<InfoResponse>,
    val ingredients: String,
    val price: PriceResponse,
    val subtitle: String,
    val tags: List<String>,
    val title: String
)

fun ProductResponse.toDomain(): ProductDomain {
    return ProductDomain(
        available = available,
        description = description,
        feedback = feedback.toDomain(),
        id = id,
        info = info.map { it.toDomain() },
        ingredients = ingredients,
        price = price.toDomain(),
        subtitle,
        tags,
        title,
        images = getImagesByProductId(id)
    )
}