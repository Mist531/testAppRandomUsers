package com.example.testapprandomusers.models

import kotlinx.serialization.Serializable

@Serializable
data class LocationModel(
    val city: String,
    val coordinates: CoordinatesModel,
    val country: String,
    val postcode: Int,
    val state: String,
    val street: StreetModel,
    val timezone: TimezoneModel
)