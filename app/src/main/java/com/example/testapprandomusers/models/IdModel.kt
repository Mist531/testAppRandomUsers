package com.example.testapprandomusers.models

import kotlinx.serialization.Serializable

@Serializable
data class IdModel(
    val name: String,
    val value: String
)