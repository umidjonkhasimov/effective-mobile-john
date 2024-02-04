package com.example.effectivemobile.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedbackDomain(
    val count: Int,
    val rating: Double
) : Parcelable