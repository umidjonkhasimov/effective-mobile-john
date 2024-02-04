package com.example.effectivemobile.presentation.main_screen.favorite_screen

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.effectivemobile.presentation.BottomNavItem
import com.example.effectivemobile.presentation.components.ProductItem
import com.example.effectivemobile.presentation.ui.theme.LightGray
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    navController: NavController
) {
    val viewModel: FavoriteViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                Spacer(modifier = Modifier.width(32.dp))
                Text(text = "Избранное", style = MaterialTheme.typography.titleSmall)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            TabBar(viewModel, navController)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabBar(viewModel: FavoriteViewModel, navController: NavController) {
    val uiState = viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState()
    val pages = listOf("Товары", "Бренды")
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }
    val scope = rememberCoroutineScope()

    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .clip(RoundedCornerShape(8.dp)),
        selectedTabIndex = pagerState.currentPage,
        indicator = indicator,
        containerColor = LightGray
    ) {
        pages.forEachIndexed { index, title ->
            Tab(
                modifier = Modifier
                    .zIndex(6f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {

                    },
                text = { Text(text = title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(index, 0f)
                    }
                },
                interactionSource = MutableInteractionSource()
            )
        }
    }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        count = pages.size,
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> {
                Box(Modifier.fillMaxSize()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)
                    ) {
                        when (uiState.value) {
                            is UIState.Loaded -> {
                                (uiState.value as UIState.Loaded).list.forEach { data ->
                                    item {
                                        ProductItem(
                                            modifier = Modifier.padding(6.dp),
                                            images = data.images,
                                            isFavorite = data.isFavorite,
                                            productData = data,
                                            onItemClick = {
                                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                                    key = "product",
                                                    value = data
                                                )
                                                navController.navigate(BottomNavItem.Details.route)
                                            },
                                            onFavoriteClick = {},
                                            onUnFavoriteClick = {
                                                viewModel.unFavoriteProduct(it)
                                            },
                                            onAddClick = {}
                                        )
                                    }
                                }
                            }

                            UIState.Loading -> {

                            }
                        }
                    }
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Brand")
                }
            }
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition = updateTransition(pagerState.currentPage)
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = ""
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .padding(6.dp)
            .fillMaxSize()
            .background(color = Color.White, RoundedCornerShape(8.dp))
            .zIndex(1f)
    )
}