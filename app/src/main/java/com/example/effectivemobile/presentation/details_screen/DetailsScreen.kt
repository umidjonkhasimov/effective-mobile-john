package com.example.effectivemobile.presentation.details_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.effectivemobile.R
import com.example.effectivemobile.domain.model.FeedbackDomain
import com.example.effectivemobile.domain.model.InfoDomain
import com.example.effectivemobile.domain.model.PriceDomain
import com.example.effectivemobile.domain.model.ProductDomain
import com.example.effectivemobile.presentation.components.ImageSlider
import com.example.effectivemobile.presentation.components.RatingBar
import com.example.effectivemobile.presentation.ui.theme.EffectiveMobileTheme
import com.example.effectivemobile.presentation.ui.theme.LightGray

@Composable
fun DetailsScreen(
    navController: NavController,
    productData: ProductDomain?
) {
    productData?.let {
        val viewModel: DetailsViewModel = hiltViewModel()
        Scaffold(
            topBar = {
                Row(modifier = Modifier.padding(16.dp)) {
                    Image(
                        modifier = Modifier.clickable(
                            indication = rememberRipple(bounded = false),
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            navController.popBackStack()
                        },
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = null
                    )
                }
            }
        ) { paddingValues ->
            var isDescriptionVisible by remember { mutableStateOf(true) }
            var isIngredientsVisible by remember { mutableStateOf(true) }
            var isFavorite by remember { mutableStateOf(productData.isFavorite) }

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Column {
                        Box {
                            ImageSlider(
                                modifier = Modifier.fillMaxWidth(),
                                sliderList = productData.images
                            )
                            Image(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .clickable(
                                        indication = rememberRipple(bounded = false),
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        if (isFavorite) {
                                            viewModel.unFavoriteProduct(productData)
                                        } else {
                                            viewModel.favoriteProduct(productData)
                                        }
                                        isFavorite = !isFavorite
                                    },
                                painter = if (isFavorite) {
                                    painterResource(R.drawable.ic_favorite_filled)
                                } else {
                                    painterResource(R.drawable.ic_favorite)
                                },
                                contentDescription = null
                            )
                        }
                        Text(
                            text = productData.title,
                            style = MaterialTheme.typography.labelSmall
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = productData.subtitle,
                            style = MaterialTheme.typography.labelLarge
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Доступно для заказа ${productData.available} штук",
                            style = MaterialTheme.typography.labelSmall
                        )

                        Divider(
                            modifier = Modifier.padding(12.dp),
                            thickness = 1.dp,
                            color = LightGray
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RatingBar(
                                filledStarIcon = R.drawable.ic_star_filled,
                                halfStarIcon = R.drawable.ic_star_unfilled,
                                rating = productData.feedback.rating
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(text = productData.feedback.rating.toString())

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(text = "▪")

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(text = "${productData.feedback.count} отзыва")
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${productData.price.priceWithDiscount}${productData.price.unit}",
                                style = MaterialTheme.typography.labelLarge,
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "${productData.price.price}${productData.price.unit}",
                                style = MaterialTheme.typography.labelSmall,
                                textDecoration = TextDecoration.LineThrough
                            )

                            Spacer(modifier = Modifier.width(12.dp))

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

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "Описание", style = MaterialTheme.typography.titleMedium)

                        Spacer(modifier = Modifier.height(12.dp))

                        AnimatedVisibility(
                            visible = isDescriptionVisible,
                            enter = fadeIn(
                                initialAlpha = 0.4f,
                            ),
                            exit = fadeOut(
                                animationSpec = tween(durationMillis = 100)
                            )
                        ) {
                            Column {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(containerColor = LightGray)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = productData.title,
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Image(
                                            imageVector = Icons.Rounded.KeyboardArrowRight,
                                            contentDescription = null
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = productData.description,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable() {
                                    isDescriptionVisible = !isDescriptionVisible
                                },
                            text = if (isDescriptionVisible) {
                                "Скрыть"
                            } else {
                                "Подробнее"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(text = "Характеристики", style = MaterialTheme.typography.titleMedium)

                        Spacer(modifier = Modifier.height(24.dp))

                        Column {
                            productData.info.forEach { info ->
                                Row(modifier = Modifier.padding(vertical = 12.dp)) {
                                    Text(
                                        text = info.title,
                                        style = MaterialTheme.typography.labelSmall
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = info.value,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                                Divider()
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row {
                            Text(text = "Состав", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                painter = painterResource(R.drawable.ic_copy),
                                contentDescription = null
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        AnimatedVisibility(
                            visible = isIngredientsVisible,
                            enter = fadeIn(
                                initialAlpha = 0.4f,
                            ),
                            exit = fadeOut(
                                animationSpec = tween(durationMillis = 100)
                            )
                        ) {
                            Column {
                                Text(
                                    text = productData.ingredients,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable {
                                    isIngredientsVisible = !isIngredientsVisible
                                },
                            text = if (isIngredientsVisible) {
                                "Скрыть"
                            } else {
                                "Подробнее"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Card(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .height(56.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${productData.price.priceWithDiscount}${productData.price.unit}"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${productData.price.price}${productData.price.unit}",
                                    style = MaterialTheme.typography.labelSmall,
                                    textDecoration = TextDecoration.LineThrough
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = "Добавить корзину")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    EffectiveMobileTheme {
        DetailsScreen(
            navController = rememberNavController(),
            ProductDomain(
                122,
                "Пенка для лица Глубокое очищение содержит минеральную воду и соду, способствует глубокому очищению пор от различных загрязнений, контролирует работу сальных желез, сужает поры. Обладает мягким антисептическим действием, не пересушивая кожу. Минеральная вода тонизирует и смягчает кожу.",
                FeedbackDomain(1, 4.2),
                "das",
                listOf(
                    InfoDomain("Артикул товара", "1324"),
                    InfoDomain("Артикул товара", "1324"),
                    InfoDomain("Артикул товара", "1324"),
                ),
                "Water, Propylene Glycol, Dipropylene Glycol, Lauric acid, Myristic Acid, Potassium Hydroxide, Lauryl Hydroxysultaine, Potassium Cocoate, Potassium Cocoyl Glycinate, Sodium Chloride, Glycerin, Phenoxyethanol, Chlorphenesin, Fragrance, Caprylyl Glycol, Ethylhexylglycerin, Capric acid, Palmitic Acid, Disodium EDTA, Sodium Bicarbonate, Cocamidopropyl Betaine, Butylene Glycol.",
                PriceDomain(50, "100", "50", "$"),
                "Пенка для умывания`A`PIEU` `DEEP CLEAN` 200 мл",
                listOf("dasdas"),
                "A`PIEU"
            )
        )
    }
}