package com.example.testapprandomusers.models.users_repositories

import kotlinx.serialization.Serializable

@Serializable
data class RegisteredModel(
    val age: Int,
    val date: String
)