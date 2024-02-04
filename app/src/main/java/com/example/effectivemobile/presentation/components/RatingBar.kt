package com.example.effectivemobile.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    @DrawableRes
    filledStarIcon: Int,
    @DrawableRes
    halfStarIcon: Int,
    starsColor: Color = Color(0xFFF9A249)
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                painter = painterResource(filledStarIcon),
                contentDescription = null,
                tint = starsColor
            )
        }
        if (halfStar) {
            Icon(
                painter = painterResource(halfStarIcon),
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unfilledStars) {
            Icon(
                painter = painterResource(filledStarIcon),
                contentDescription = null,
                tint = starsColor.copy(alpha = 0.2f)
            )
        }
    }
}