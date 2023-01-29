package com.example.testapprandomusers.models.exeptions

import arrow.optics.optics
import com.example.testapprandomusers.data.exception.AppException
import kotlinx.serialization.Serializable

@optics
@Serializable
data class ExceptionDataStoreModel(
    val listException: List<AppException> = emptyList(),
){
    companion object
}