package com.example.effectivemobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.effectivemobile.presentation.AppNavigation
import com.example.effectivemobile.presentation.Screen
import com.example.effectivemobile.presentation.ui.theme.EffectiveMobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EffectiveMobileTheme {
                val viewModel = hiltViewModel<MainViewModel>()
                val isRegistered = viewModel.isRegistered.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (isRegistered.value) {
                        true -> {
                            AppNavigation(Screen.Main)
                        }

                        false -> {
                            AppNavigation(Screen.Registration)
                        }

                        null -> {}
                    }
                }
            }
        }
    }
}