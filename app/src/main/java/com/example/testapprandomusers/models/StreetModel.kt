package com.example.testapprandomusers.models

import kotlinx.serialization.Serializable

@Serializable
data class StreetModel(
    val name: String,
    val number: Int
)