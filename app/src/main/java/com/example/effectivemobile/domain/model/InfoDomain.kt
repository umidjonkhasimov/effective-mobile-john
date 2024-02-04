package com.example.effectivemobile.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoDomain(
    val title: String,
    val value: String
): Parcelable