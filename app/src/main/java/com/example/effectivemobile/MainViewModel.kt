package com.example.effectivemobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivemobile.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _isRegistered: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isRegistered = _isRegistered.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.isUserRegistered.collectLatest {
                _isRegistered.value = it
            }
        }
    }
}