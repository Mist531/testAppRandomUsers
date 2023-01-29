package com.example.testapprandomusers.models.users_repositories

import kotlinx.serialization.Serializable

@Serializable
data class IdModel(
    val name: String,
    val value: String?
)