package com.example.effectivemobile.presentation.main_screen.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivemobile.data.local.ProductDatabase
import com.example.effectivemobile.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val database: ProductDatabase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.userFirstName.collectLatest { firstName ->
                _uiState.update { oldState ->
                    oldState.copy(firstName = firstName)
                }
            }
        }
        viewModelScope.launch {
            dataStoreRepository.userLastName.collectLatest { lastName ->
                _uiState.update { oldState ->
                    oldState.copy(lastName = lastName)
                }
            }
        }
        viewModelScope.launch {
            dataStoreRepository.userPhoneNumber.collectLatest { phoneNumber ->
                _uiState.update { oldState ->
                    oldState.copy(phoneNumber = phoneNumber)
                }
            }
        }
        viewModelScope.launch {
            database.productDao().getRowCount().collectLatest { favorites ->
                _uiState.update { oldState ->
                    oldState.copy(favoriteAmount = favorites.toString())
                }
            }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            database.clearAllTables()
            dataStoreRepository.clearDatastore()
        }
    }
}

data class UIState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val favoriteAmount: String = ""
)