package com.example.effectivemobile.presentation.main_screen.favorite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivemobile.domain.model.ProductDomain
import com.example.effectivemobile.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {
    var uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            productsRepository.getAllFavorites().collect { productList ->
                uiState.value = UIState.Loaded(productList)
            }
        }
    }

    fun unFavoriteProduct(productDomain: ProductDomain) {
        viewModelScope.launch {
            productsRepository.upsertItem(productDomain.copy(isFavorite = false))
        }
    }
}

sealed interface UIState {
    data object Loading : UIState
    data class Loaded(val list: List<ProductDomain>) : UIState
}