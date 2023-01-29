package com.example.testapprandomusers.models.users_repositories

import kotlinx.serialization.Serializable

@Serializable
data class InfoModel(
    val page: Int,
    val results: Int,
    val seed: String,
    val version: String
)