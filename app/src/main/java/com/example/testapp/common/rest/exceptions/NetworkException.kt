package com.example.testapp.common.rest.exceptions

import com.example.testapp.common.rest.response.*
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkException
private constructor(val url: String,
                    val type: Type,
                    val responseCode: String,
                    val description: String? = null,
                    val originalCause: Throwable? = null) : RuntimeException(originalCause) {

    enum class Type {
        /**
         * non-200 HTTP status code was received from the server
         */
        HTTP,
        /**
         * IOException occurred while communicating to the server
         */
        NETWORK,
        /**
         * internal error occurred while attempting to execute a request
         */
        UNKNOWN,
        /**
         * server response contains error flag or is invalid
         */
        SERVER
    }

    companion object {

        fun httpError(url: String, throwable: HttpException) =
                NetworkException(url, Type.HTTP, HTTP, "${throwable.code()}: ${throwable.message()}",
                        throwable)

        fun networkError(url: String, throwable: IOException) = when (throwable) {
            is NoInternetException -> {
                NetworkException(url, Type.NETWORK, NO_INTERNET, throwable.message, throwable)
            }
            is SocketTimeoutException -> {
                NetworkException(url, Type.NETWORK, TIMEOUT, throwable.message, throwable)
            }
            else -> {
                NetworkException(url, Type.NETWORK, UNKNOWN, throwable.message, throwable)
            }
        }

        fun unknownError(url: String, throwable: Throwable) =
                NetworkException(url, Type.UNKNOWN, UNKNOWN, throwable.message, throwable)

        fun serverError(url: String, responseCode: String, description: String?) =
                NetworkException(url, Type.SERVER, responseCode, description)

        fun invalidResponseError(url: String, description: String) =
                NetworkException(url, Type.SERVER, INVALID_RESPONSE, description)
    }

    override val message: String
        get() = "\n" + "url: $url\n" +
                "type: ${type.name}, response code: $responseCode, description: $description, " +
                "message: ${super.message}"
}