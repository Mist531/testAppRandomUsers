package com.example.testapprandomusers.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.testapprandomusers.models.ResultModel
import com.example.testapprandomusers.screens.MainNavGraph
import com.example.testapprandomusers.viewmodel.CardUsersScreenEvent
import com.example.testapprandomusers.viewmodel.CardUsersViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@MainNavGraph(start = true)
@Destination
@Composable
fun CardUsersScreen(
    navigator: DestinationsNavigator,
    viewModel: CardUsersViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    viewModel.pushSignal(CardUsersScreenEvent.GetInfo)
    LaunchedEffect(state){
        Log.e("TAG", "CardUsersScreen: $state")
    }
    LazyHorizontalGrid(
        rows = GridCells.Adaptive(200.dp),
        modifier = Modifier
            .fillMaxSize(),

    ){
        itemsIndexed(state.usersInfo) { index, userInfo ->
            CardUser(
                userInfo = userInfo,
                index = index
            )
        }
    }
}

@Composable
fun CardUser(
    userInfo: ResultModel,
    index: Int
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(100.dp)
            .height(180.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SubcomposeAsyncImage(
                model = userInfo.picture.large,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            )
            Text(
                text = "${userInfo.name.first} ${userInfo.name.last}",
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomCenter),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = "$index",
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopStart),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}