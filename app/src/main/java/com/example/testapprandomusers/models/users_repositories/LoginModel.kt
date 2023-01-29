package com.example.testapprandomusers.models.users_repositories

import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
data class LoginModel(
    val md5: String,
    val password: String,
    val salt: String,
    val sha1: String,
    val sha256: String,
    val username: String,
    val uuid: UUID
)