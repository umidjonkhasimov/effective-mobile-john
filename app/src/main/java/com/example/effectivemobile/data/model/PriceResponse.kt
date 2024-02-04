package com.example.effectivemobile.data.model

import com.example.effectivemobile.domain.model.PriceDomain

data class PriceResponse(
    val discount: Int,
    val price: String,
    val priceWithDiscount: String,
    val unit: String
)

fun PriceResponse.toDomain(): PriceDomain {
    return PriceDomain(
        discount = discount,
        price = price,
        priceWithDiscount = priceWithDiscount,
        unit = unit
    )
}