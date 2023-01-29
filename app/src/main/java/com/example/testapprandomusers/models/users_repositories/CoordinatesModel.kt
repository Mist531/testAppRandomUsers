package com.example.testapprandomusers.models.users_repositories

import kotlinx.serialization.Serializable

@Serializable
data class CoordinatesModel(
    val latitude: String,
    val longitude: String
)