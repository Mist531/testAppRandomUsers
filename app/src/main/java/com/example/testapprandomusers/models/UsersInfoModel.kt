package com.example.testapprandomusers.models

import kotlinx.serialization.Serializable

@Serializable
data class UsersInfoModel(
    val info: InfoModel,
    val results: List<ResultModel>
)