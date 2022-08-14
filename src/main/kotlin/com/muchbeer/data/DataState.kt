package com.muchbeer.data

import io.ktor.http.*

sealed class DataResponse<T>(
    val httpsStatus : HttpStatusCode = HttpStatusCode.OK
)  {
    data class SuccessResponse<T> (
        val data : T? = null,
        val message : String? = null
    ) : DataResponse<T>()

    data class ErrorResponse<T>(
        val exception: T? = null,
        val message: String? = null
    ) : DataResponse<T>()
}
