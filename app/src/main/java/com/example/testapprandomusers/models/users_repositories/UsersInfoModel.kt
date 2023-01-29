package com.example.testapprandomusers.models.users_repositories

import kotlinx.serialization.Serializable

@Serializable
data class UsersInfoModel(
    val info: InfoModel,
    val results: List<ResultModel>
)