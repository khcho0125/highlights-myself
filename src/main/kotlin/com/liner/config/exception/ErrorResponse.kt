package com.liner.config.exception

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val serial: String,
) {
    constructor(exception: DomainException) : this(
        message = exception.message ?: exception.code.defaultMessage,
        serial = exception.code.serial(),
    )
}
