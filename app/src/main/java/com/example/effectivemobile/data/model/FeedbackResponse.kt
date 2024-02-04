package com.example.effectivemobile.data.model

import com.example.effectivemobile.domain.model.FeedbackDomain

data class FeedbackResponse(
    val count: Int,
    val rating: Double
)

fun FeedbackResponse.toDomain(): FeedbackDomain {
    return FeedbackDomain(
        count = count,
        rating = rating
    )
}