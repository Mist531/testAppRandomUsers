package com.example.testapprandomusers.screens.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.testapprandomusers.R
import com.example.testapprandomusers.screens.bottom_sheet.LogBottomSheetParams
import com.example.testapprandomusers.screens.destinations.LogBottomSheetDestination
import com.example.testapprandomusers.screens.navigation.SettingsNavGraph
import com.example.testapprandomusers.ui.theme.AppTheme
import com.example.testapprandomusers.viewmodel.SettingsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@SettingsNavGraph(true)
@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = AppTheme.colors.black
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navigator.navigate(
                    LogBottomSheetDestination(
                        LogBottomSheetParams(
                            exceptions = state.exception
                        )
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = AppTheme.colors.red
            ),
            modifier = Modifier
                .padding(AppTheme.padding.default)
        ) {
            Text(text = stringResource(R.string.settings_log))
        }
    }
}