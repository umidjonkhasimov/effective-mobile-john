package com.example.effectivemobile.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PriceDomain(
    val discount: Int,
    val price: String,
    val priceWithDiscount: String,
    val unit: String
) : Parcelable