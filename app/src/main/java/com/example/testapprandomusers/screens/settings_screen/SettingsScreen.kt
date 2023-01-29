package com.example.testapprandomusers.screens.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.testapprandomusers.data.exception.exceptionHandling
import com.example.testapprandomusers.data.stores.AppExceptionStore
import com.example.testapprandomusers.screens.MainNavGraph
import com.example.testapprandomusers.screens.SpacerHeight
import com.example.testapprandomusers.ui.theme.AppTheme
import com.example.testapprandomusers.viewmodel.SettingsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getKoin
import org.koin.androidx.compose.koinViewModel

@MainNavGraph
@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val appExceptionStore: AppExceptionStore by getKoin().inject()
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
                appExceptionStore.pushSignal(
                    AppExceptionStore.AppExceptionStoreSignals.AddAppException(
                        exceptionHandling(
                            Throwable("Test exception"),
                        )
                    )
                )
            }
        ) {
            Text(text = "exception")
        }
        SpacerHeight(10)
        Button(
            onClick = {
                appExceptionStore.pushSignal(
                    AppExceptionStore.AppExceptionStoreSignals.ClearAppException
                )
            }
        ) {
            Text(text = "Clear exception")
        }
        SpacerHeight(10)
        Text(
            text = state.exception.toString(),
            color = AppTheme.colors.white
        )
    }
}