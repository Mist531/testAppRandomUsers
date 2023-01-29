package com.example.testapprandomusers.screens.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testapprandomusers.R
import com.example.testapprandomusers.models.users_repositories.ResultModel
import com.example.testapprandomusers.screens.MainNavGraph
import com.example.testapprandomusers.ui.theme.AppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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
            .fillMaxSize()
            .background(color = AppTheme.colors.darkGrey),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UserInfoBlock(
            title = stringResource(R.string.user_info_name_last_first),
            info = "${user.name.first} ${user.name.last}"
        )
        UserInfoBlock(
            title = stringResource(R.string.info_user_dob),
            info = timeParse(user.dob.date) +
                    stringResource(R.string.info_user_age) +
                    user.dob.age
        )
        UserInfoBlock(
            title = stringResource(R.string.user_info_registered_date),
            info = timeParse(user.registered.date)
        )
        UserInfoBlock(
            title = stringResource(R.string.info_user_location),
            info = stringResource(R.string.info_user_country) +
                    user.location.country +
                    stringResource(R.string.info_user_city) +
                    user.location.city
        )
        UserInfoBlock(
            title = stringResource(R.string.user_info_phone),
            info = user.phone
        )
    }
}

@Composable
fun UserInfoBlock(
    title: String,
    info: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(AppTheme.padding.small)
            .wrapContentSize()
            .sizeIn(
                minWidth = 40.dp,
                minHeight = 30.dp
            )
            .clip(RoundedCornerShape(percent = 20))
            .background(AppTheme.colors.grey2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(vertical = AppTheme.padding.small),
            color = AppTheme.colors.white,
            textAlign = TextAlign.Center,
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.padding.default),
            thickness = 1.dp,
            color = AppTheme.colors.grey1
        )
        Text(
            text = info,
            modifier = Modifier.padding(vertical = AppTheme.padding.small),
            color = AppTheme.colors.white,
            textAlign = TextAlign.Center,
        )
    }
}

fun timeParse(
    time: String,
): String {
    return ZonedDateTime.parse(time)
        .toLocalDateTime().atZone(ZoneOffset.UTC)
        .withZoneSameInstant(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd MMMM yyyy г. HH:mm", Locale.forLanguageTag("RU")))
}