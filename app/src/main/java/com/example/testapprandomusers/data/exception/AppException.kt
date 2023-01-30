package com.example.testapprandomusers.data.exception

import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.nio.channels.UnresolvedAddressException

@Serializable
data class AppException(
    val message: String,
    val date: Instant = Clock.System.now()
)

fun exceptionHandling(exception: Throwable): AppException {
    return when (exception) {
        is UnresolvedAddressException -> {
            AppException(
                message = "No connections to the server"
            )
        }
        is NoTransformationFoundException, is DoubleReceiveException -> {
            AppException(
                message = exception.message.orEmpty()
            )
        }
        else -> {
            AppException(
                message = if (exception.message?.isNotEmpty() == true && exception.message != null) {
                    exception.message.orEmpty()
                } else {
                    "No connections to the server"
                }
            )
        }
    }
}