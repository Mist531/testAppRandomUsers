package com.example.testapprandomusers.models.users_repositories

import kotlinx.serialization.Serializable

@Serializable
data class LocationModel(
    val city: String,
    val coordinates: CoordinatesModel,
    val country: String,
    val postcode: String,
    val state: String,
    val street: StreetModel,
    val timezone: TimezoneModel
)