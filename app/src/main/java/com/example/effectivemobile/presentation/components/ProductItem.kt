package com.example.effectivemobile.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.effectivemobile.R
import com.example.effectivemobile.domain.model.FeedbackDomain
import com.example.effectivemobile.domain.model.InfoDomain
import com.example.effectivemobile.domain.model.PriceDomain
import com.example.effectivemobile.domain.model.ProductDomain
import com.example.effectivemobile.presentation.ui.theme.EffectiveMobileTheme
import com.example.effectivemobile.presentation.ui.theme.LightGray

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    images: List<Int>,
    isFavorite: Boolean,
    productData: ProductDomain,
    onItemClick: (ProductDomain) -> Unit,
    onFavoriteClick: (ProductDomain) -> Unit,
    onUnFavoriteClick: (ProductDomain) -> Unit,
    onAddClick: (ProductDomain) -> Unit
) {
    var isFavoriteState by remember {
        mutableStateOf(isFavorite)
    }

    Card(
        modifier = modifier
            .height(300.dp)
            .width(168.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clickable() {
                onItemClick(productData)
            },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                ImageSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.5f)
                        .background(Color.White),
                    sliderList = images,
                    dotsActiveColor = MaterialTheme.colorScheme.primary,
                    dotsInActiveColor = LightGray
                )
                Column(
                    modifier = Modifier
                        .weight(.5f)
                        .padding(start = 6.dp)
                ) {
                    Text(
                        text = "${productData.price.price}${productData.price.unit}",
                        style = MaterialTheme.typography.labelSmall,
                        textDecoration = TextDecoration.LineThrough
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row {
                        Text(
                            text = "${productData.price.priceWithDiscount}${productData.price.unit}",
                            style = MaterialTheme.typography.labelLarge
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .width(34.dp)
                                .height(16.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(size = 4.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "-${productData.price.discount}%",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = productData.title,
                        style = MaterialTheme.typography.labelLarge
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = productData.subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null
                        )
                        Text(
                            text = productData.feedback.rating.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFF9A249)
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = "(${productData.feedback.count})",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .height(32.dp)
                    .width(32.dp)
                    .clickable {
                        onAddClick(productData)
                    },
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null
            )

            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .clickable(
                        indication = rememberRipple(bounded = false),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        if (isFavoriteState) {
                            onUnFavoriteClick(productData)
                        } else {
                            onFavoriteClick(productData)
                        }
                        isFavoriteState = !isFavoriteState
                    },
                painter = if (isFavoriteState)
                    painterResource(R.drawable.ic_favorite_filled)
                else painterResource(
                    id = R.drawable.ic_favorite
                ), contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    EffectiveMobileTheme {
        val list = listOf(
            ProductDomain(
                122,
                "asfdfas",
                FeedbackDomain(122, 4.3),
                "1",
                info = listOf(InfoDomain("fassd", "dsads")),
                "dassafsas",
                PriceDomain(50, "100", "50", "$"),
                "adsfegrwefavfdgr",
                listOf("das", "fasd"),
                "feasgf"
            ),
            ProductDomain(
                122,
                "asfdfas",
                FeedbackDomain(122, 4.3),
                "2",
                info = listOf(InfoDomain("fassd", "dsads")),
                "dassafsas",
                PriceDomain(50, "100", "50", "$"),
                "adsfegrwefavfdgr",
                listOf("das", "fasd"),
                "feasgf"
            ),
            ProductDomain(
                122,
                "asfdfas",
                FeedbackDomain(122, 4.3),
                "3",
                info = listOf(InfoDomain("fassd", "dsads")),
                "dassafsas",
                PriceDomain(50, "100", "50", "$"),
                "adsfegrwefavfdgr",
                listOf("das", "fasd"),
                "feasgf"
            ), ProductDomain(
                122,
                "asfdfas",
                FeedbackDomain(122, 4.3),
                "4",
                info = listOf(InfoDomain("fassd", "dsads")),
                "dassafsas",
                PriceDomain(50, "100", "50", "$"),
                "adsfegrwefavfdgr",
                listOf("das", "fasd"),
                "feasgf"
            )
        )
        LazyColumn(modifier = Modifier.padding(12.dp)) {
            list.forEach { productDomain ->
                item(key = productDomain.id) {
                    ProductItem(
                        images = listOf(
                            R.drawable.ic_launcher_background,
                            R.drawable.ic_launcher_background,
                            R.drawable.ic_launcher_background
                        ),
                        isFavorite = true,
                        productData = productDomain,
                        onItemClick = {
                            Log.d("GGG", "ProductItemPreview: item")
                        },
                        onFavoriteClick = {
                            Log.d("GGG", "ProductItemPreview: fav")
                        },
                        onUnFavoriteClick = {

                        },
                        onAddClick = {
                            Log.d("GGG", "ProductItemPreview: add")
                        }
                    )
                }

            }
        }
    }
}