package com.example.effectivemobile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.effectivemobile.R
import com.example.effectivemobile.presentation.ui.theme.LightGray
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ImageSlider(
    modifier: Modifier = Modifier,
    sliderList: List<Int>,
    dotsActiveColor: Color = MaterialTheme.colorScheme.primary,
    dotsInActiveColor: Color = LightGray,
    dotsSize: Dp = 4.dp,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 0.dp),
    imageCornerRadius: Dp = 0.dp,
    imageHeight: Dp = 250.dp,
    imageWidth: Dp = 250.dp,
) {
    val pagerState = rememberPagerState(0)
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .width(imageWidth)
            .height(imageHeight)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        HorizontalPager(
            modifier = modifier.weight(1f),
            state = pagerState,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = pagerPaddingValues,
            key = null,
            count = sliderList.size
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffset

            val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)

            Box(modifier = modifier
                .graphicsLayer {
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                .alpha(scaleFactor.coerceIn(0f, 1f))
                .clip(RoundedCornerShape(imageCornerRadius))
            ) {
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    model = sliderList[page],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            indicatorWidth = dotsSize,
            activeColor = dotsActiveColor,
            inactiveColor = dotsInActiveColor,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(6.dp)
        )
    }
}

@Preview
@Composable
private fun ImageSliderPreview() {
    ImageSlider(
        sliderList = listOf(
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background
        )
    )
}