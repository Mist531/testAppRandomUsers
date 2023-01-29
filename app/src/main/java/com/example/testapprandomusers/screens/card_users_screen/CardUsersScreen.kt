package com.example.testapprandomusers.screens.card_users_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.testapprandomusers.models.ResultModel
import com.example.testapprandomusers.screens.MainNavGraph
import com.example.testapprandomusers.screens.destinations.SelectUserBottomSheetDestination
import com.example.testapprandomusers.viewmodel.CardUsersScreenEvent
import com.example.testapprandomusers.viewmodel.CardUsersViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

//region Preview
@Preview(showBackground = false)
@Composable
fun PreviewNumberPerson() {
    NumberPerson(
        index = 1
    )
}

@Preview(showBackground = false)
@Composable
fun NamePersonPreview() {
    NamePerson(
        nameFirst = "John",
        nameLast = "Doe"
    )
}
//endregion


@OptIn(ExperimentalMaterialApi::class)
@MainNavGraph(start = true)
@Destination
@Composable
fun CardUsersScreen(
    navigator: DestinationsNavigator,
    viewModel: CardUsersViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = {
            viewModel.pushSignal(
                CardUsersScreenEvent.GetInfo(
                    page = state.pageInfo?.page ?: 1
                )
            )
        }
    )

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ) {
        VerticalGridPerson(
            usersInfoList = state.usersInfo,
            onClick = { userInfo ->
                navigator.navigate(
                    SelectUserBottomSheetDestination(
                        userInfo
                    )
                )
            }
        )
        PullRefreshIndicator(
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun VerticalGridPerson(
    modifier: Modifier = Modifier,
    usersInfoList: List<ResultModel>,
    onClick: (ResultModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier
            .fillMaxSize(),
    ) {
        itemsIndexed(usersInfoList) { index, userInfo ->
            CardUser(
                userInfo = userInfo,
                index = index,
                modifier = Modifier
                    .clickable {
                        onClick(userInfo)
                    }
            )
        }
    }
}


@Composable
fun CardUser(
    modifier: Modifier = Modifier,
    userInfo: ResultModel,
    index: Int
) {
    Column(
        modifier = modifier

            .padding(10.dp)
            .clip(
                RoundedCornerShape(10.dp)
            )
            .wrapContentSize()
            .background(Color.DarkGray),
       // shape = RoundedCornerShape(10.dp),
    ) {
        Box(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            SubcomposeAsyncImage(
                model = userInfo.picture.large,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colors.primary
                    )
                }
            )
            NumberPerson(
                index = index + 1,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
            )
        }
        NamePerson(
            nameFirst = userInfo.name.first,
            nameLast = userInfo.name.last,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        )
    }
}


@Composable
fun NumberPerson(
    index: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .sizeIn(
                minWidth = 20.dp,
                maxWidth = 50.dp
            )
            .clip(RoundedCornerShape(percent = 50))
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$index",
            modifier = Modifier.padding(horizontal = 5.dp),
            style = MaterialTheme.typography.h5,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 15.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun NamePerson(
    nameFirst: String,
    nameLast: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .sizeIn(
                minWidth = 40.dp,
                minHeight = 30.dp
            )
            .clip(RoundedCornerShape(percent = 20))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$nameFirst $nameLast",
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colors.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}