package com.example.effectivemobile.presentation.main_screen.catalog_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivemobile.domain.model.ProductDomain
import com.example.effectivemobile.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {
    private val allItems = mutableListOf<ProductDomain>()
    private val allTags = mutableListOf<Tags>()
    private val allSortingOptions = mutableListOf<Sort>()
    private val _uiState = MutableStateFlow(UIState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val data = productsRepository.getProducts()
            val sortedList = data.sortedByDescending {
                it.feedback.rating
            }

            allItems.addAll(sortedList)

            Tags.entries.forEach { tag ->
                allTags.add(tag)
            }

            Sort.entries.forEach { sort ->
                allSortingOptions.add(sort)
            }

            _uiState.update { oldState ->
                oldState.copy(
                    tags = allTags,
                    sortOptions = allSortingOptions,
                    items = allItems,
                    isLoading = false
                )
            }
        }
    }

    fun sortByTag(tag: Tags) {
        when (tag) {
            Tags.ALL -> {
                _uiState.update { oldState ->
                    oldState.copy(
                        items = allItems
                    )
                }
            }

            Tags.BODY -> {
                _uiState.update { oldState ->
                    oldState.copy(
                        items = allItems.filter {
                            it.tags.contains(tag.tag)
                        }
                    )
                }
            }

            Tags.FACE -> {
                _uiState.update { oldState ->
                    oldState.copy(
                        items = allItems.filter {
                            it.tags.contains(tag.tag)
                        }
                    )
                }
            }

            Tags.MASK -> {
                _uiState.update { oldState ->
                    oldState.copy(
                        items = allItems.filter {
                            it.tags.contains(tag.tag)
                        }
                    )
                }
            }

            Tags.SUNTAN -> {
                _uiState.update { oldState ->
                    oldState.copy(
                        items = allItems.filter {
                            it.tags.contains(tag.tag)
                        }
                    )
                }
            }
        }
    }

    fun sortItems(sort: Sort) {
        val oldItems = _uiState.value.items

        when (sort) {
            Sort.BY_RATING -> {
                _uiState.update { oldState ->
                    oldState.copy(
                        items = oldItems.sortedByDescending {
                            it.feedback.rating
                        }
                    )
                }
            }

            Sort.BY_PRICE_ASCENDING -> {
                _uiState.update { oldState ->
                    oldState.copy(
                        items = oldItems.sortedBy {
                            it.price.priceWithDiscount
                        }
                    )
                }
            }

            Sort.BY_PRICE_DESCENDING -> {
                _uiState.update { oldState ->
                    oldState.copy(
                        items = oldItems.sortedByDescending {
                            it.price.priceWithDiscount
                        }
                    )
                }
            }
        }
    }

    fun setItemFavorite(productDomain: ProductDomain) {
        viewModelScope.launch {
            productsRepository.upsertItem(productDomain.copy(isFavorite = true))
        }
    }

    fun setItemUnFavorite(productDomain: ProductDomain) {
        viewModelScope.launch {
            productsRepository.upsertItem(productDomain.copy(isFavorite = false))
        }
    }
}

data class UIState(
    val tags: List<Tags> = emptyList(),
    val sortOptions: List<Sort> = emptyList(),
    val items: List<ProductDomain> = emptyList(),
    val isLoading: Boolean = false
)

enum class Tags(val tag: String, val label: String) {
    ALL("all", "Смотреть все"),
    BODY("body", "Тело"),
    FACE("face", "Лицо"),
    MASK("mask", "Массаж"),
    SUNTAN("suntan", "Загар")
}

enum class Sort(val title: String) {
    BY_RATING("По популярности"),
    BY_PRICE_ASCENDING("По возрастанию цены"),
    BY_PRICE_DESCENDING("По уменьшению цены"),
}