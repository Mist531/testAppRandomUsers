package com.example.testapprandomusers.screens.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.testapprandomusers.screens.destinations.SettingsScreenDestination
import com.example.testapprandomusers.ui.theme.AppTheme

@Composable
fun TopBar(
    navController: NavController,
    settingsOnClick: () -> Unit,
    homeOnClick: () -> Unit
) {
    val currentDestination by navController.currentBackStackEntryAsState()
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Random Users",
                    style = MaterialTheme.typography.h6,
                    color = AppTheme.colors.white,
                )

                if (currentDestination?.destination?.route == SettingsScreenDestination.route) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(30.dp)
                            .clickable {
                                homeOnClick()
                            },
                        tint = AppTheme.colors.white,
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(30.dp)
                            .clickable {
                                settingsOnClick()
                            },
                        tint = AppTheme.colors.white,
                    )
                }

            }
        },
        backgroundColor = AppTheme.colors.black,
        contentColor = AppTheme.colors.black,
    )
}