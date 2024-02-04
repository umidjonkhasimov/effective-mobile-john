package com.example.effectivemobile.presentation.registration_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivemobile.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    fun onEventDispatcher(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.OnFirstNameChange -> {
                _uiState.update {
                    it.copy(
                        firstName = uiEvent.firstName,
                        isValidFirstName = validateName(uiEvent.firstName)
                    )
                }
            }

            is UiEvent.OnLastNameChange -> {
                _uiState.update {
                    it.copy(
                        lastName = uiEvent.lastName,
                        isValidLastName = validateName(uiEvent.lastName)
                    )
                }
            }

            is UiEvent.OnPhoneNumberChange -> {
                _uiState.update {
                    it.copy(
                        phoneNumber = uiEvent.phoneNumber,
                        isValidPhoneNumber = validatePhone(uiEvent.phoneNumber)
                    )
                }
            }

            UiEvent.OnLogIn -> {
                viewModelScope.launch {
                    _uiState.value.apply {
                        dataStoreRepository.setFirstName(firstName)
                        dataStoreRepository.setLastName(lastName)
                        dataStoreRepository.setPhoneNumber(phoneNumber)
                        dataStoreRepository.setIsUserRegistered(true)
                    }
                }
            }
        }
    }

    private fun validateName(name: String): Boolean {
        return name.isNotBlank() && name.matches("[а-яА-Я]+".toRegex())
    }

    private fun validatePhone(phoneNumber: String): Boolean {
        return phoneNumber.isNotBlank() &&
                phoneNumber.startsWith("7") &&
                phoneNumber.length == 11
    }
}

sealed class UiEvent {
    data class OnFirstNameChange(val firstName: String) : UiEvent()
    data class OnLastNameChange(val lastName: String) : UiEvent()
    data class OnPhoneNumberChange(val phoneNumber: String) : UiEvent()
    data object OnLogIn : UiEvent()
}

data class UIState(
    val firstName: String = "",
    val isValidFirstName: Boolean? = null,
    val lastName: String = "",
    val isValidLastName: Boolean? = null,
    val phoneNumber: String = "",
    val isValidPhoneNumber: Boolean? = null
)