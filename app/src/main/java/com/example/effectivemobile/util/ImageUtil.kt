package com.example.effectivemobile.util

import com.example.effectivemobile.R

fun getImagesByProductId(id: String): List<Int> {
    when (id) {
        "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> {
            return listOf(R.drawable.img6, R.drawable.img5)
        }

        "54a876a5-2205-48ba-9498-cfecff4baa6e" -> {
            return listOf(R.drawable.img1, R.drawable.img2)
        }

        "75c84407-52e1-4cce-a73a-ff2d3ac031b3" -> {
            return listOf(R.drawable.img5, R.drawable.img6)
        }

        "16f88865-ae74-4b7c-9d85-b68334bb97db" -> {
            return listOf(R.drawable.img3, R.drawable.img4)
        }

        "26f88856-ae74-4b7c-9d85-b68334bb97db" -> {
            return listOf(R.drawable.img2, R.drawable.img3)
        }

        "15f88865-ae74-4b7c-9d81-b78334bb97db" -> {
            return listOf(R.drawable.img6, R.drawable.img1)
        }

        "88f88865-ae74-4b7c-9d81-b78334bb97db" -> {
            return listOf(R.drawable.img4, R.drawable.img3)
        }

        else -> {
            return listOf(R.drawable.img1, R.drawable.img5)
        }
    }
}