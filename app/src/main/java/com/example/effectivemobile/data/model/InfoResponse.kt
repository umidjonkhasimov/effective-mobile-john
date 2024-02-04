package com.example.effectivemobile.data.model

import com.example.effectivemobile.domain.model.InfoDomain

data class InfoResponse(
    val title: String,
    val value: String
)

fun InfoResponse.toDomain(): InfoDomain {
    return InfoDomain(
        title = title,
        value = value
    )
}