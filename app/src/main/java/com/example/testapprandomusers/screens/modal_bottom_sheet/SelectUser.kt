package com.example.testapprandomusers.screens.modal_bottom_sheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.testapprandomusers.models.ResultModel
import com.example.testapprandomusers.screens.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle

@Destination(
    style = DestinationStyle.BottomSheet::class
)
@MainNavGraph
@Composable
fun SelectUserBottomSheet(
    selectUser: ResultModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        UserInfo(
            modifier = Modifier,
            user = selectUser
        )
    }
}

@Composable
fun UserInfo(
    modifier: Modifier,
    user: ResultModel
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = user.name.first)
        Text(text = user.name.last)
        Text(text = user.email)
        Text(text = user.phone)
        Text(text = user.cell)
        Text(text = user.location.city)
        Text(text = user.location.state)
        Text(text = user.location.country)
        Text(text = user.location.postcode)
    }
}
