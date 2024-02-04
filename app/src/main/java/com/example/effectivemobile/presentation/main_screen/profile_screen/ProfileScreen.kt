package com.example.effectivemobile.presentation.main_screen.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.effectivemobile.R
import com.example.effectivemobile.presentation.BottomNavItem
import com.example.effectivemobile.presentation.ui.theme.EffectiveMobileTheme
import com.example.effectivemobile.presentation.ui.theme.LightGray

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Личный кабинет", style = MaterialTheme.typography.titleSmall)
            }
        }
    ) { paddingValues ->
        val userFirstName = uiState.value.firstName
        val userLastName = uiState.value.lastName
        val userPhoneNumber = uiState.value.phoneNumber
        val userFavProducts = uiState.value.favoriteAmount
        var shouldShowDialog by remember { mutableStateOf(false) }

        if (shouldShowDialog) {
            AlertDialog(
                onDismissRequest = { shouldShowDialog = false },
                text = { Text(text = "Do you want to sign out?") },
                title = { Text(text = "Are you sure?") },
                dismissButton = {
                    TextButton(
                        onClick = { shouldShowDialog = false }
                    ) {
                        Text(text = "No")
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.clearDatabase()
                            shouldShowDialog = false
                        }
                    ) {
                        Text(text = "Yes")
                    }
                },
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = LightGray)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "$userFirstName $userLastName",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = userPhoneNumber,
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(R.drawable.ic_logout),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            navController.navigate(BottomNavItem.Favorite.route)
                        },
                    colors = CardDefaults.cardColors(containerColor = LightGray)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_favorite),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Избранное",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "$userFavProducts товар",
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = LightGray)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_market),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Магазины",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = LightGray)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_feedback),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Обратная связь",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = LightGray)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_offer),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Оферта",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = LightGray)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_return),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Возврат товара",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        shouldShowDialog = true
                    },
                colors = CardDefaults.cardColors(containerColor = LightGray)
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Выйти",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    EffectiveMobileTheme {
//        ProfileScreen()
    }
}