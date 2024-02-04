package com.example.effectivemobile.presentation.details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivemobile.domain.model.ProductDomain
import com.example.effectivemobile.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {
    fun unFavoriteProduct(productDomain: ProductDomain) {
        viewModelScope.launch {
            productsRepository.upsertItem(productDomain.copy(isFavorite = false))
        }
    }

    fun favoriteProduct(productData: ProductDomain) {
        viewModelScope.launch {
            productsRepository.upsertItem(productData.copy(isFavorite = true))
        }
    }
}