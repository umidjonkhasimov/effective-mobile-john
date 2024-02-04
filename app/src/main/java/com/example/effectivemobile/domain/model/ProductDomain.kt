package com.example.effectivemobile.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ProductDomain(
    val available: Int,
    val description: String,
    val feedback: FeedbackDomain,
    @PrimaryKey
    val id: String,
    val info: List<InfoDomain>,
    val ingredients: String,
    val price: PriceDomain,
    val subtitle: String,
    val tags: List<String>,
    val title: String,
    val isFavorite: Boolean = false,
    val images: List<Int> = emptyList()
) : Parcelable