package com.example.effectivemobile.presentation.registration_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.effectivemobile.presentation.Screen
import com.example.effectivemobile.presentation.components.PhoneTextField
import com.example.effectivemobile.presentation.components.TextField

@Composable
fun RegistrationScreen(navController: NavHostController) {
    val viewModel: RegistrationViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Screen.Registration.name
                )
            }
        }
    ) { paddingValues ->
        val buttonEnabledState by remember {
            derivedStateOf {
                uiState.isValidFirstName ?: false
                        && uiState.isValidLastName ?: false
                        && uiState.isValidPhoneNumber ?: false
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                TextField(
                    value = uiState.firstName,
                    label = "Имя",
                    isValidText = uiState.isValidFirstName ?: true,
                    onValueChange = { newValue ->
                        viewModel.onEventDispatcher(UiEvent.OnFirstNameChange(newValue))
                    },
                    onCleared = {
                        viewModel.onEventDispatcher(UiEvent.OnFirstNameChange(""))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = uiState.lastName,
                    label = "Фамилия",
                    isValidText = uiState.isValidLastName ?: true,
                    onValueChange = { newValue ->
                        viewModel.onEventDispatcher(UiEvent.OnLastNameChange(newValue))
                    },
                    onCleared = {
                        viewModel.onEventDispatcher(UiEvent.OnLastNameChange(""))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                PhoneTextField(
                    value = uiState.phoneNumber,
                    label = "Номер Телефона",
                    isValidPhone = uiState.isValidPhoneNumber ?: true,
                    onValueChange = { newValue ->
                        viewModel.onEventDispatcher(UiEvent.OnPhoneNumberChange(newValue))
                    },
                    onCleared = {
                        viewModel.onEventDispatcher(UiEvent.OnPhoneNumberChange(""))
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.onEventDispatcher(UiEvent.OnLogIn)
                        navController.navigate(
                            route = Screen.Main.route,
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(Screen.Registration.route, true)
                                .build()
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = buttonEnabledState,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Войти")
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier,
                    text = "Нажимая кнопку “Войти”, Вы принимаете",
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier,
                    text = "условия программы лояльности",
                    fontSize = 10.sp,
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}