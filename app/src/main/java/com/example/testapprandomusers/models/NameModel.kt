package com.example.testapprandomusers.models

import kotlinx.serialization.Serializable

@Serializable
data class NameModel(
    val first: String,
    val last: String,
    val title: String
)