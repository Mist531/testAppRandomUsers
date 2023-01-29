package com.example.testapprandomusers.models.users_repositories

import kotlinx.serialization.Serializable

@Serializable
data class TimezoneModel(
    val description: String,
    val offset: String
)