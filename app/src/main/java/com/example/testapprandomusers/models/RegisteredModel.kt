package com.example.testapprandomusers.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisteredModel(
    val age: Int,
    val date: String
)