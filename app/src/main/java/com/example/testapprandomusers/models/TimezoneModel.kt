package com.example.testapprandomusers.models

import kotlinx.serialization.Serializable

@Serializable
data class TimezoneModel(
    val description: String,
    val offset: String
)