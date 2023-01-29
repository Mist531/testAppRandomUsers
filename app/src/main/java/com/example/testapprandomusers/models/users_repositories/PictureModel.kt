package com.example.testapprandomusers.models.users_repositories

import kotlinx.serialization.Serializable

@Serializable
data class PictureModel(
    val large: String,
    val medium: String,
    val thumbnail: String
)