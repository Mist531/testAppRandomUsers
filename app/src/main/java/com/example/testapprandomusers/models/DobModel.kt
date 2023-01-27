package com.example.testapprandomusers.models

import kotlinx.serialization.Serializable

@Serializable
data class DobModel(
    val age: Int,
    val date: String
)