package com.example.testapprandomusers.screens.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.testapprandomusers.data.exception.AppException
import com.example.testapprandomusers.screens.navigation.SettingsNavGraph
import com.example.testapprandomusers.ui.theme.AppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlinx.serialization.Serializable

@Serializable
data class LogBottomSheetParams(
    val exceptions: List<AppException>
)

@Destination(
    style = DestinationStyle.BottomSheet::class
)
@SettingsNavGraph
@Composable
fun LogBottomSheet(
    navigator: DestinationsNavigator,
    logBottomSheetParams: LogBottomSheetParams
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = AppTheme.colors.darkGrey
            )
    ) {
        Text(
            color = AppTheme.colors.red,
            text = "Log",
            modifier = Modifier
                .padding(AppTheme.padding.small)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
        LazyColumn(
            state = rememberLazyListState()
        ) {
            items(logBottomSheetParams.exceptions) { exception ->
                ExceptionItem(
                    exception = exception
                )
            }
        }
    }
}

@Composable
fun ExceptionItem(
    modifier: Modifier = Modifier,
    exception: AppException
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .padding(AppTheme.padding.small)
    ) {
        Text(
            text = "message: " +
                    exception.message +
                    ",\ntime: " +
                    timeParse(exception.date.toString()),
            color = AppTheme.colors.white,
            textAlign = TextAlign.Start,
        )
    }
}