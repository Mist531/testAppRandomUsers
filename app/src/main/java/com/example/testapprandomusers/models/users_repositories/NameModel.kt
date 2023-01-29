package com.example.testapprandomusers.models.users_repositories

import kotlinx.serialization.Serializable

@Serializable
data class NameModel(
    val first: String,
    val last: String,
    val title: String
)