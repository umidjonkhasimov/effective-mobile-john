package com.example.effectivemobile.presentation.main_screen.catalog_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.effectivemobile.R
import com.example.effectivemobile.presentation.BottomNavItem
import com.example.effectivemobile.presentation.components.DropDownMenu
import com.example.effectivemobile.presentation.components.FilterChipLazyRow
import com.example.effectivemobile.presentation.components.ProductItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CatalogScreen(
    navController: NavController
) {
    val viewModel: CatalogViewModel = hiltViewModel()
    val state = viewModel.uiState.collectAsState()
    var currentSortOption by remember { mutableStateOf(Sort.BY_RATING) }
    var selectedTag by remember { mutableStateOf(Tags.ALL) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Каталог", style = MaterialTheme.typography.titleSmall)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropDownMenu(
                        sortOptions = state.value.sortOptions,
                        currentSortOption = currentSortOption,
                        onClick = {
                            viewModel.sortItems(it)
                            currentSortOption = it
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(R.drawable.ic_filter),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = "Фильтры")
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                FilterChipLazyRow(
                    tags = state.value.tags,
                    selected = selectedTag,
                    onSelect = { tag ->
                        viewModel.sortByTag(tag)
                        selectedTag = tag
                    },
                    onDeselect = { tag ->
                        viewModel.sortByTag(Tags.ALL)
                        selectedTag = Tags.ALL
                    }
                )
            }

            item {
                val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2 - 12.dp)
                when (state.value.isLoading) {
                    true -> {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    false -> {
                        FlowRow {
                            state.value.items.forEach { productData ->
                                ProductItem(
                                    modifier = Modifier
                                        .width(itemSize)
                                        .padding(6.dp),
                                    images = productData.images,
                                    isFavorite = productData.isFavorite,
                                    productData = productData,
                                    onItemClick = {
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            key = "product",
                                            value = productData
                                        )
                                        navController.navigate(BottomNavItem.Details.route)
                                    },
                                    onFavoriteClick = {
                                        viewModel.setItemFavorite(it)
                                    },
                                    onUnFavoriteClick = {
                                        viewModel.setItemUnFavorite(it)
                                    },
                                    onAddClick = {}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}