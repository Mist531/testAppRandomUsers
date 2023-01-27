package com.example.testapprandomusers.models

import kotlinx.serialization.Serializable

@Serializable
data class CoordinatesModel(
    val latitude: String,
    val longitude: String
)