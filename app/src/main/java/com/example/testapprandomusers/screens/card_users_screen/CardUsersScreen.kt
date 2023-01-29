package com.example.testapprandomusers.screens.card_users_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.testapprandomusers.models.users_repositories.ResultModel
import com.example.testapprandomusers.screens.MainNavGraph
import com.example.testapprandomusers.screens.destinations.SelectUserBottomSheetDestination
import com.example.testapprandomusers.ui.theme.AppTheme
import com.example.testapprandomusers.viewmodel.CardUsersScreenEvent
import com.example.testapprandomusers.viewmodel.CardUsersState
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
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .pullRefresh(state = pullRefreshState)
                .fillMaxSize()
                .background(AppTheme.colors.black)

        ) {
            VerticalGridPerson(
                modifier = Modifier
                    .weight(1f,false),
                state = state,
                usersInfoList = state.usersInfo,
                onClick = { userInfo ->
                    navigator.navigate(
                        SelectUserBottomSheetDestination(
                            userInfo
                        )
                    )
                }
            )
            PageSwitch(
                state = state,
                modifier = Modifier
                    .height(70.dp),
                onClickNext = {
                    viewModel.pushSignal(
                        CardUsersScreenEvent.NextPage
                    )
                },
                onClickPrev = {
                    viewModel.pushSignal(
                        CardUsersScreenEvent.PrevPage
                    )
                }
            )
        }
        PullRefreshIndicator(
            refreshing = state.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

//region page switch
@Composable
fun PageSwitch(
    state: CardUsersState,
    modifier: Modifier,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit
) {
    Row(
        modifier = modifier
            .background(AppTheme.colors.black)
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PageIconButton(
            modifier = Modifier,
            imageVector = Icons.Filled.ArrowBack,
            onClick = {
                onClickPrev()
            }
        )
        Text(
            text = "${state.pageInfo?.page ?: 1}/${state.maxPage}",
            color = AppTheme.colors.white,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
        )
        PageIconButton(
            modifierIcon = Modifier
                .rotate(180f),
            imageVector = Icons.Filled.ArrowBack,
            onClick = {
                onClickNext()
            }
        )
    }
}

@Composable
fun PageIconButton(
    modifierIcon: Modifier = Modifier,
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(percent = 10)),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppTheme.colors.grey,
            contentColor = Color.Red
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = modifierIcon
                .wrapContentHeight(),
            tint = AppTheme.colors.black
        )
    }
}
//endregion

//region persons card
@Composable
fun VerticalGridPerson(
    state: CardUsersState,
    modifier: Modifier = Modifier,
    usersInfoList: List<ResultModel>,
    onClick: (ResultModel) -> Unit
) {
    val stateLazyGrid = rememberLazyGridState()

    LaunchedEffect(state.pageInfo?.page) {
        stateLazyGrid.animateScrollToItem(0)
    }

    LazyVerticalGrid(
        state = stateLazyGrid,
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
            .padding(AppTheme.padding.small)
            .clip(
                RoundedCornerShape(
                    AppTheme.shapes.small
                )
            )
            .wrapContentSize()
            .background(AppTheme.colors.darkGrey),
    ) {
        Box(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(AppTheme.shapes.small))
        ) {
            SubcomposeAsyncImage(
                model = userInfo.picture.large,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center),
                        color = AppTheme.colors.white
                    )
                }
            )
            NumberPerson(
                index = index + 1,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(AppTheme.padding.small)
            )
        }
        NamePerson(
            nameFirst = userInfo.name.first,
            nameLast = userInfo.name.last,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(AppTheme.padding.small)
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
            color = AppTheme.colors.white,
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
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$nameFirst $nameLast",
            modifier = Modifier
                .padding(5.dp),
            color = AppTheme.colors.white,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}
//endregion