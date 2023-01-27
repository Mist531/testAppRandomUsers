package com.example.testapprandomusers.repositories

import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import java.nio.channels.UnresolvedAddressException
import java.util.Calendar
import java.util.Date

interface AppException {
    val message: String
    val date: Date
}

abstract class NetworkException(
    override val message: String
) : AppException {
    override val date: Date = Calendar.getInstance().time

    data class ServerError(
        override val message: String
    ) : NetworkException(
        "Server response exception: $message"
    )

    data class JSONParseException(
        override val message: String
    ) : NetworkException(
        "Parsing json exception: $message"
    )

    data class UnknownException(
        override val message: String
    ) : NetworkException(
        "Unknown exception: $message"
    )
}

fun exceptionHandling(exception: Throwable): NetworkException {
    return when (exception) {
        is UnresolvedAddressException -> {
            NetworkException.ServerError(
                "Нет соединения с сервером"
            )
        }

        is NoTransformationFoundException, is DoubleReceiveException -> {
            NetworkException.JSONParseException(
                exception.message.orEmpty()
            )
        }

        else -> {
            NetworkException.UnknownException(
                if (exception.message?.isNotEmpty() == true && exception.message != null) {
                    exception.message.orEmpty()
                } else {
                    "Нет соединения с сервером"
                }
            )
        }
    }
}